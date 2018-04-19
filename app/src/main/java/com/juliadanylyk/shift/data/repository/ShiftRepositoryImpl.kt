package com.juliadanylyk.shift.data.repository

import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.network.ShiftService
import com.juliadanylyk.shift.utils.Converter
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

class ShiftRepositoryImpl(private val shiftService: ShiftService) : ShiftRepository {

    override suspend fun getShifts(): List<Shift> {
        val result = shiftService.getShifts().awaitResult()
        return when (result) {
            is Result.Ok -> Converter.toShifts(result.value)
            else -> listOf()
        }
    }
}