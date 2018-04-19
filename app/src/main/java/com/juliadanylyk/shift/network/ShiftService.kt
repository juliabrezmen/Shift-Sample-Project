package com.juliadanylyk.shift.network

import retrofit2.Call
import retrofit2.http.GET

interface ShiftService {

    @GET("/dmc/shifts")
    fun getShifts(): Call<List<ShiftResult>>
}