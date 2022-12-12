package com.hamza.fleetiosample.feature_vehicle.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

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
    val color: String,
    val secondaryMeter: Boolean,
    val createdAt: LocalDateTime?,
    val specs: Specs,
    val page: Int
): Parcelable