package com.juliadanylyk.shift.network

import com.google.gson.annotations.SerializedName

data class ShiftResult(
        @SerializedName("id") val id: Int,
        @SerializedName("start") val startTime: String,
        @SerializedName("end") val endTime: String?,
        @SerializedName("startLatitude") val startLatitude: String,
        @SerializedName("startLongitude") val startLongitude: String,
        @SerializedName("endLatitude") val endLatitude: String?,
        @SerializedName("endLongitude") val endLongitude: String?,
        @SerializedName("image") val imageUrl: String)