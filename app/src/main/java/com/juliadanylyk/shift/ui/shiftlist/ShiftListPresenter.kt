package com.juliadanylyk.shift.ui.shiftlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.juliadanylyk.Dispatcher
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.data.repository.ShiftRepository
import com.juliadanylyk.shift.navigator.Navigator
import com.juliadanylyk.shift.network.RequestResult
import com.juliadanylyk.shift.ui.common.SHIFT_DETAILS_REQUEST_CODE
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class ShiftListPresenter(private val view: ShiftListContract.View,
                         private val shiftRepository: ShiftRepository,
                         private val dispatcher: Dispatcher,
                         private val navigator: Navigator) : LifecycleObserver, ShiftListContract.Presenter {

    private val job: Job = Job()
    private var shifts: List<Shift> = listOf()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startPresenting() {
        loadShifts()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopPresenting() {
        job.cancel()
    }

    override fun onShiftClicked(shift: Shift) {
        navigator.openShiftDetailsScreen(shift, SHIFT_DETAILS_REQUEST_CODE)
    }

    override fun onAddShiftClicked() {
        val currentShiftInProgress = shifts.find { it.inProgress() }
        navigator.openShiftDetailsScreen(currentShiftInProgress, SHIFT_DETAILS_REQUEST_CODE)
    }

    override fun onPullToRefresh() {
        loadShifts()
    }

    override fun onActivityResultOk(requestCode: Int) {
        if (SHIFT_DETAILS_REQUEST_CODE == requestCode) {
            loadShifts()
        }
    }

    private fun loadShifts() = launch(dispatcher.ui, parent = job) {
        view.showLoading()
        val result = withContext(dispatcher.background) { shiftRepository.getShifts() }
        view.hideLoading()
        when (result) {
            is RequestResult.Success<List<Shift>> -> updateData(result.data)
            is RequestResult.Failure -> view.showError()
        }
    }

    private fun updateData(data: List<Shift>) {
        shifts = data.sortedByDescending { it.startTime }
        view.updateShifts(shifts)
        if (shifts.isEmpty()) {
            view.showEmptyView()
        } else {
            view.hideEmptyView()
        }
    }
}