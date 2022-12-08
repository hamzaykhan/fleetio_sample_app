package com.hamza.fleetiosample.feature_vehicle.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VehicleSpecEntity(
    @PrimaryKey(autoGenerate = true) val specId: Int? = null,
    val vehicleId: Int,
    val engineBrand: String,
    val cargoVolume: Double,
    val engineStrokes: Double,
    val engineDescription: String,
    val fuelTankCapacity: Double,
    val transmissionGear: Int
)
