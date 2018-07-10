package com.juliadanylyk.shift.ui.shiftlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.juliadanylyk.Dispatcher
import com.juliadanylyk.shift.Dependencies
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.navigator.Navigator
import com.juliadanylyk.shift.network.RequestResult
import com.juliadanylyk.shift.repository.ShiftRepository
import com.juliadanylyk.shift.ui.common.SHIFT_DETAILS_REQUEST_CODE
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class ShiftListPresenter(private val view: ShiftListContract.View,
                         private val shiftRepository: ShiftRepository,
                         private val dispatcher: Dispatcher,
                         private val navigator: Navigator) : LifecycleObserver, ShiftListContract.Presenter {

    private val job: Job = Job()
    private var viewState = ShiftListContract.ViewState()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startPresenting() {
        loadShifts()

        val shifts = LivePagedListBuilder(Dependencies.shiftDao.getShifts(), PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
//                .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
                .build()).build()
        shifts.observeForever { view.render(viewState.copy(pagedShifts = it)) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopPresenting() {
        job.cancel()
    }

    override fun onShiftClicked(shift: Shift) {
        navigator.openShiftDetailsScreen(shift, SHIFT_DETAILS_REQUEST_CODE)
    }

    override fun onAddShiftClicked() {
        val currentShiftInProgress = viewState.shifts.find { it.inProgress() }
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
        changeState(viewState.copy(loadingVisible = true))

        val result = withContext(dispatcher.background) { shiftRepository.getShifts() }
        when (result) {
            is RequestResult.Success<List<Shift>> -> changeState(viewState.copy(
                    shifts = result.data.sortedByDescending { it.startTime },
                    emptyViewVisible = result.data.isEmpty(),
                    loadingVisible = false))
            is RequestResult.Failure -> {
                changeState(viewState.copy(loadingVisible = false, showInternetConnectionError = true))
                changeState(viewState.copy(showInternetConnectionError = false))
            }
        }
    }

    private fun changeState(state: ShiftListContract.ViewState) {
        viewState = state
        view.render(viewState)
    }
}