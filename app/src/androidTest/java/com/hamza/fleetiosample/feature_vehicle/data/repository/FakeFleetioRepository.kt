package com.hamza.fleetiosample.feature_vehicle.data.repository

import com.hamza.fleetiosample.common.repository.FleetioRepository
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.domain.repository.CommentRepository
import com.hamza.fleetiosample.feature_vehicle.domain.repository.VehicleRepository

class FakeFleetioRepository(
    private val database: FleetioDatabase
): FleetioRepository {

    override fun getVehicleRepository(): VehicleRepository {
        return FakeVehicleRepository(database)
    }

    override fun getCommentRepository(): CommentRepository {
        return FakeCommentRepository(database)
    }
}