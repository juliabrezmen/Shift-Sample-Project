package com.juliadanylyk.shift.data.repository

import com.juliadanylyk.shift.data.Shift

interface ShiftRepository {
    suspend fun getShifts(): List<Shift>
}