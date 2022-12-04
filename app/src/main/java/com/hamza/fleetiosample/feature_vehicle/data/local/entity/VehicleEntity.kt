package com.hamza.fleetiosample.feature_vehicle.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VehicleEntity(
    @PrimaryKey val id: Int? = null,
    val name: String
)