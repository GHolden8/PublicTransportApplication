package com.example.mapstest


import com.google.android.gms.maps.model.LatLng

data class BusStop(
    val latLng: LatLng,
    val title: String,
    val snippet: String,
    val color: Float,
    val route: Int
)