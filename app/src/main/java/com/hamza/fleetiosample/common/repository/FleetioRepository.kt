package com.hamza.fleetiosample.common.repository

import com.hamza.fleetiosample.feature_vehicle.domain.repository.CommentRepository
import com.hamza.fleetiosample.feature_vehicle.domain.repository.VehicleRepository
import javax.inject.Inject

interface FleetioRepository {

    fun getVehicleRepository(): VehicleRepository
    fun getCommentRepository(): CommentRepository

}

class FleetioRepositoryImpl @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val commentRepository: CommentRepository
): FleetioRepository {

    override fun getVehicleRepository(): VehicleRepository {
        return vehicleRepository
    }

    override fun getCommentRepository(): CommentRepository {
        return commentRepository
    }

}