package com.juliadanylyk.shift.navigator

import com.juliadanylyk.shift.data.Shift

interface Navigator {

    fun exitCurrentScreen()

    fun exitWithResultCodeOk()

    //todo: leave a comment to explain when shift can be null
    fun openShiftDetailsScreen(shift: Shift?, requestCode: Int)
}