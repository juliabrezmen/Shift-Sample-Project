package com.juliadanylyk.shift.ui.shiftdetails

import com.juliadanylyk.shift.data.Shift

interface ShiftDetailsContract {

    interface Presenter {
        fun onBackClicked()

        fun onStartShiftClicked()

        fun onEndShiftClicked()
    }

    interface View {
        fun getShiftFromExtras(): Shift?

        fun initNewState()

        fun initInProgressState(startTime: Long, startLatitude: Double, startLongitude: Double, image: String)

        fun initCompletedState(startTime: Long, startLatitude: Double, startLongitude: Double,
                               endTime: Long, endLatitude: Double, endLongitude: Double, image: String)

        fun showLoading()

        fun hideLoading()

        fun showError()
    }
}