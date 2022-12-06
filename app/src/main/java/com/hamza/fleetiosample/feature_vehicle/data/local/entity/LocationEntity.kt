package com.hamza.fleetiosample.feature_vehicle.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val locationId: Int? = null,
    val vehicleId: Int,
    val latitude: Double,
    val longitude: Double
)