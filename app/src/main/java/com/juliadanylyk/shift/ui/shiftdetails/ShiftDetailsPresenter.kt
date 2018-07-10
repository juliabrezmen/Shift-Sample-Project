package com.juliadanylyk.shift.ui.shiftdetails

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.juliadanylyk.Dispatcher
import com.juliadanylyk.location.LocationManager
import com.juliadanylyk.shift.R
import com.juliadanylyk.shift.repository.ShiftRepository
import com.juliadanylyk.shift.navigator.Navigator
import com.juliadanylyk.shift.network.RequestResult
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class ShiftDetailsPresenter(private val view: ShiftDetailsContract.View,
                            private val shiftRepository: ShiftRepository,
                            private val locationManager: LocationManager,
                            private val dispatcher: Dispatcher,
                            private val navigator: Navigator) : LifecycleObserver, ShiftDetailsContract.Presenter {

    private val job: Job = Job()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startPresenting() {
        val shift = view.getShiftFromExtras()
        val state = when {
            shift == null -> ShiftState.New()
            shift.inProgress() -> ShiftState.InProgress(shift.startTime, shift.startLatitude, shift.startLongitude, shift.image)
            else -> ShiftState.Completed(
                    shift.startTime, shift.startLatitude, shift.startLongitude,
                    shift.endTime!!, shift.endLatitude!!, shift.endLongitude!!,
                    shift.image)
        }

        when (state) {
            is ShiftState.New -> view.initNewState()
            is ShiftState.InProgress -> view.initInProgressState(state.startTime, state.startLatitude, state.startLongitude, state.image)
            is ShiftState.Completed -> view.initCompletedState(state.startTime, state.startLatitude, state.startLongitude,
                    state.endTime, state.endLatitude, state.endLongitude, state.image)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopPresenting() {
        job.cancel()
    }

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onStartShiftClicked() {
        view.requestLocationPermission { granted ->
            if (granted) requestStartLocation()
        }
    }

    override fun onEndShiftClicked() {
        view.requestLocationPermission { granted ->
            if (granted) requestEndLocation()
        }
    }

    private fun requestStartLocation() = launch(dispatcher.ui, parent = job) {
        val location = locationManager.getLastLocation()
        if (location != null) {
            changeShift { shiftRepository.startShift(System.currentTimeMillis(), location.latitude, location.longitude) }
        } else {
            view.showToast(R.string.location_not_found)
        }
    }

    private fun requestEndLocation() = launch(dispatcher.ui, parent = job) {
        val lastLocation = locationManager.getLastLocation()
        if (lastLocation != null) {
            changeShift { shiftRepository.endShift(System.currentTimeMillis(), lastLocation.latitude, lastLocation.longitude) }
        } else {
            view.showToast(R.string.location_not_found)
        }
    }

    private suspend fun changeShift(change: suspend () -> RequestResult<Unit>) {
        view.showLoading()
        val result = withContext(dispatcher.background) { change() }
        when (result) {
            is RequestResult.Success -> navigator.exitWithResultCodeOk()
            is RequestResult.Failure -> view.showToast(R.string.internet_connection_error)
        }
        view.hideLoading()
    }

    sealed class ShiftState {

        class New : ShiftState()

        data class InProgress(val startTime: Long,
                              val startLatitude: Double,
                              val startLongitude: Double,
                              val image: String) : ShiftState()

        class Completed(val startTime: Long,
                        val startLatitude: Double,
                        val startLongitude: Double,
                        val endTime: Long,
                        val endLatitude: Double,
                        val endLongitude: Double,
                        val image: String) : ShiftState()
    }
}