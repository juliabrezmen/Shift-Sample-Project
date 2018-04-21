package com.juliadanylyk.shift.ui.shiftlist

import com.juliadanylyk.shift.data.Shift

interface ShiftListContract {

    interface Presenter {
        fun onShiftClicked(shift: Shift)
    }

    interface View {
        fun updateShifts(shifts: List<Shift>)

        fun showEmptyView()

        fun hideEmptyView()

        fun showLoading()

        fun hideLoading()
    }
}