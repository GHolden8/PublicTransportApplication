package com.example.mapstest.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import android.app.Activity


class LocationManager(private val context: Context) {

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
    }

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 10000 //sets how often updates are received
        fastestInterval = 5000 //maximum rate of updates
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY //accuracy and power
    }//end of location request

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                // Handle the location update
            }
        }
    }//end of locationCallback

    fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        } else {
            // Permission is not granted, so we need to request it
            ActivityCompat.requestPermissions(
                context as Activity, // assuming 'context' is an instance of an Activity
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }//end of startLocation updates

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }//ends the lcoation updates

}

