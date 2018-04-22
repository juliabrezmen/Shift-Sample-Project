package com.juliadanylyk.shift.utils

import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.network.ShiftDto
import com.juliadanylyk.shift.network.ShiftResult

class Converter {

    companion object {
        fun toShifts(results: List<ShiftResult>): List<Shift> {
            val shifts = mutableListOf<Shift>()
            results.forEach { result ->
                //todo: try to remove !!
                shifts.add(Shift(result.id,
                        DateUtils.parseUtcDate(result.startTime)!!,
                        DateUtils.parseUtcDate(result.endTime),
                        result.startLatitude.toDouble(),
                        result.startLongitude.toDouble(),
                        result.endLatitude?.toDouble(),
                        result.endLongitude?.toDouble(),
                        result.imageUrl))
            }
            return shifts
        }

        fun toShiftDto(time: Long, latitude: Double, longitude: Double) =
                ShiftDto(DateUtils.toUtcDate(time), latitude.toString(), longitude.toString())
    }
}