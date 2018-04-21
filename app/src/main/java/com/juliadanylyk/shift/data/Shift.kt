package com.juliadanylyk.shift.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shift(val id: Int,
                 val startTime: Long,
                 val endTime: Long?,
                 val startLatitude: Double,
                 val startLongitude: Double,
                 val endLatitude: Double?,
                 val endLongitude: Double?,
                 val image: String) : Parcelable