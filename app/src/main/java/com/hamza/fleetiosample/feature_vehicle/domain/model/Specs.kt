package com.hamza.fleetiosample.feature_vehicle.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Specs(
    val engineBrand: String,
    val cargoVolume: Double,
    val engineStrokes: Double,
    val engineDescription: String,
    val fuelTankCapacity: Double,
    val transmissionGear: Int
): Parcelable