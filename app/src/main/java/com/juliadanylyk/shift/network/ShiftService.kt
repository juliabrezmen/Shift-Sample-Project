package com.juliadanylyk.shift.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShiftService {

    @GET("/dmc/shifts")
    fun getShifts(): Call<List<ShiftResult>>

    @POST("/dmc/shift/start")
    fun startShift(@Body request: ShiftDto): Call<ResponseBody>

    @POST("/dmc/shift/end")
    fun endShift(@Body request: ShiftDto): Call<ResponseBody>
}