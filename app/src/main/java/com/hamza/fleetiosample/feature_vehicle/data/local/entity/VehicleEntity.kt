package com.hamza.fleetiosample.feature_vehicle.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hamza.fleetiosample.feature_vehicle.domain.model.LocationItem

@Entity
data class VehicleEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val defaultUrl: String,
    val make: String,
    val model: String,
    val meterName: String,
    val year: Int,
    val vin: String,
    val licensePlate: String,
    val page: Int,
    val isViewed: Boolean = false
)