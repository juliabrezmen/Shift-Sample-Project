package com.juliadanylyk.shift.ui.shiftlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.data.repository.ShiftRepository
import com.juliadanylyk.shift.navigator.Navigator
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class ShiftListPresenter(private val view: ShiftListContract.View,
                         private val shiftRepository: ShiftRepository,
                         private val navigator: Navigator) : LifecycleObserver, ShiftListContract.Presenter {

    private val job: Job = Job()

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

    private fun loadShifts() = launch(context = UI, parent = job) {
        val shifts = withContext(CommonPool) { shiftRepository.getShifts() }
        view.updateShifts(shifts)
        view.hideLoading()
        if (shifts.isEmpty()) {
            view.showEmptyView()
        } else {
            view.hideEmptyView()
        }
    }
}