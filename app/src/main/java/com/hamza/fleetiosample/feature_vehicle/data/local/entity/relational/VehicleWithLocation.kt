package com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational

import androidx.room.Embedded
import androidx.room.Relation
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.LocationEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleSpecEntity

data class VehicleWithLocation(
    @Embedded
    val vehicle: VehicleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "vehicleId",
    )
    val location: LocationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "vehicleId",
    )
    val spec: VehicleSpecEntity
)