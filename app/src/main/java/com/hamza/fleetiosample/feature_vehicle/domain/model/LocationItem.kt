package com.hamza.fleetiosample.feature_vehicle.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationItem(
    val latitude: Double,
    val longitude: Double
): Parcelable