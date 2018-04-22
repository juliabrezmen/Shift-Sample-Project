package com.juliadanylyk.location

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.experimental.suspendCancellableCoroutine

class LocationManagerImpl(activity: Activity) : LocationManager {

    private var locationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation() = suspendCancellableCoroutine<Location?> { continuation ->
        val lastLocation = locationClient.lastLocation
        lastLocation.addOnSuccessListener { location: Location? -> continuation.resume(location) }
        lastLocation.addOnFailureListener { continuation.resume(null) }
        lastLocation.addOnCanceledListener { continuation.cancel() }
    }

}