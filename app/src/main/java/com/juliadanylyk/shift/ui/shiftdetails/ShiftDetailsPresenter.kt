package com.juliadanylyk.shift.ui.shiftdetails

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.juliadanylyk.shift.data.repository.ShiftRepository
import com.juliadanylyk.shift.navigator.Navigator
import com.juliadanylyk.shift.network.RequestResult
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class ShiftDetailsPresenter(private val view: ShiftDetailsContract.View,
                            private val shiftRepository: ShiftRepository,
                            private val navigator: Navigator) : LifecycleObserver, ShiftDetailsContract.Presenter {

    private val job: Job = Job()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startPresenting() {
        val shift = view.getShiftFromExtras()
        val state = when {
            shift == null -> ShiftState.New()
            shift.endTime == null -> ShiftState.InProgress(shift.startTime, shift.startLatitude, shift.startLongitude, shift.image)
            else -> ShiftState.Completed(shift.startTime, shift.startLatitude, shift.startLongitude,
                    shift.endTime, shift.endLatitude!!, shift.endLongitude!!, shift.image)
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
        changeShift { shiftRepository.startShift(System.currentTimeMillis(), 89.5, -139.3) }
    }

    override fun onEndShiftClicked() {
        changeShift { shiftRepository.endShift(System.currentTimeMillis(), 89.5, -139.3) }
    }

    private fun changeShift(change: suspend () -> RequestResult<Unit>) = launch(context = UI, parent = job) {
        view.showLoading()
        val result = withContext(CommonPool) { change() }
        view.hideLoading()
        when (result) {
            is RequestResult.Success -> navigator.exitWithResultCodeOk()
            is RequestResult.Failure -> view.showError()
        }
    }

    sealed class ShiftState {
        class New : ShiftState()
        data class InProgress(val startTime: Long, val startLatitude: Double, val startLongitude: Double, val image: String) : ShiftState()
        class Completed(val startTime: Long, val startLatitude: Double, val startLongitude: Double,
                        val endTime: Long, val endLatitude: Double, val endLongitude: Double, val image: String) : ShiftState()
    }
}