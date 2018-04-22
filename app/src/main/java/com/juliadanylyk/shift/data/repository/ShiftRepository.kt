package com.juliadanylyk.shift.data.repository

import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.network.RequestResult

interface ShiftRepository {

    suspend fun getShifts(): RequestResult<List<Shift>>

    suspend fun startShift(time: Long, latitude: Double, longitude: Double): RequestResult<Unit>

    suspend fun endShift(time: Long, latitude: Double, longitude: Double): RequestResult<Unit>
}