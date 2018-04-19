package com.juliadanylyk.shift.ui.shiftlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.juliadanylyk.shift.Dependencies.DEPENDENCIES
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class ShiftListPresenter(private val view: ShiftListContract.View) : LifecycleObserver, ShiftListContract.Presenter {
    private val job: Job = Job()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startPresenting() {
        loadShifts()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopPresenting() {
        view.updateShifts(listOf())
    }

    private fun loadShifts() = launch(context = UI, parent = job) {
        val shifts = withContext(CommonPool) { DEPENDENCIES.shiftRepository.getShifts() }
        view.updateShifts(shifts)
        if (shifts.isEmpty()) {
            view.showEmptyView()
        } else {
            view.hideEmptyView()
        }
    }
}