package com.juliadanylyk.shift.navigator

import com.juliadanylyk.shift.data.Shift

interface Navigator {

    fun exitCurrentScreen()

    //todo: leave a comment
    fun openShiftDetailsScreen(shift: Shift?)
}