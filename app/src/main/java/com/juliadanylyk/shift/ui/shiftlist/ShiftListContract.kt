package com.juliadanylyk.shift.ui.shiftlist

import com.juliadanylyk.shift.data.Shift

interface ShiftListContract {

    interface Presenter {
        fun onShiftClicked(shift: Shift)

        fun onAddShiftClicked()

        fun onPullToRefresh()

        fun onActivityResultOk(requestCode: Int)
    }

    interface View {
        fun render(state: ViewState)
    }

    data class ViewState(
            val emptyViewVisible: Boolean = true,
            val loadingVisible: Boolean = false,
            val showInternetConnectionError: Boolean = false,
            val shifts: List<Shift> = emptyList()
    )
}