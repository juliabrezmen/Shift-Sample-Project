package com.juliadanylyk.shift.ui.shiftlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.data.repository.ShiftRepository
import com.juliadanylyk.shift.navigator.Navigator
import com.juliadanylyk.shift.network.RequestResult
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class ShiftListPresenter(private val view: ShiftListContract.View,
                         private val shiftRepository: ShiftRepository,
                         private val navigator: Navigator) : LifecycleObserver, ShiftListContract.Presenter {

    private val job: Job = Job()
    private var shifts: List<Shift> = listOf()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startPresenting() {
        view.showLoading()
        loadShifts()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopPresenting() {
        job.cancel()
    }

    override fun onShiftClicked(shift: Shift) {
        navigator.openShiftDetailsScreen(shift)
    }

    override fun onAddShiftClicked() {
        val currentShiftInProgress = shifts.find { it.inProgress() }
        navigator.openShiftDetailsScreen(currentShiftInProgress)
    }

    private fun loadShifts() = launch(context = UI, parent = job) {
        val result = withContext(CommonPool) { shiftRepository.getShifts() }
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