package com.juliadanylyk.shift.navigator

import com.juliadanylyk.shift.data.Shift

interface Navigator {

    fun exitCurrentScreen()

    fun openShiftDetailsScreen(shift: Shift)
}