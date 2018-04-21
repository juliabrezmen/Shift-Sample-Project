package com.juliadanylyk.shift.network

import com.google.gson.annotations.SerializedName

data class ShiftDto(@SerializedName("time") val time: String,
                    @SerializedName("latitude") val latitude: String,
                    @SerializedName("longitude") val longitude: String)