package com.juliadanylyk.shift.repository

import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.network.RequestResult
import com.juliadanylyk.shift.network.ShiftService
import com.juliadanylyk.shift.utils.Converter
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

class ShiftRepositoryImpl(private val shiftService: ShiftService) : ShiftRepository {

    override suspend fun getShifts(): RequestResult<List<Shift>> {
        val result = shiftService.getShifts().awaitResult()
        return when (result) {
            is Result.Ok -> RequestResult.Success(Converter.toShifts(result.value))
            else -> RequestResult.Failure()
        }
    }

    override suspend fun startShift(time: Long, latitude: Double, longitude: Double): RequestResult<Unit> {
        val result = shiftService.startShift(Converter.toShiftDto(time, latitude, longitude)).awaitResult()
        return when (result) {
            is Result.Ok -> RequestResult.Success(Unit)
            else -> RequestResult.Failure()
        }
    }

    override suspend fun endShift(time: Long, latitude: Double, longitude: Double): RequestResult<Unit> {
        val result = shiftService.endShift(Converter.toShiftDto(time, latitude, longitude)).awaitResult()
        return when (result) {
            is Result.Ok -> RequestResult.Success(Unit)
            else -> RequestResult.Failure()
        }
    }
}