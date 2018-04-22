package com.juliadanylyk.location

import android.location.Location

interface LocationManager {
    suspend fun getLastLocation(): Location?
}