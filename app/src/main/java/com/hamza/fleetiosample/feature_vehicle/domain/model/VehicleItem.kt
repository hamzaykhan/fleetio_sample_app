package com.hamza.fleetiosample.feature_vehicle.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleItem(
    val id: Int,
    val name: String,
    val defaultUrl: String,
    val make: String,
    val model: String,
    val meterName: String,
    val year: Int,
    val vin: String,
    val licensePlate: String,
    val location: LocationItem,
    val page: Int
): Parcelable
