package com.hamza.fleetiosample.feature_vehicle.domain.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {

    suspend fun getVehicles(
        page: Int,
        filter: VehicleFilter,
        fetchLocal: Boolean
    ): Flow<Resource<List<VehicleItem>>>

    suspend fun insertVehicles(vehicles: List<VehicleWithLocation>)

    suspend fun getLocalVehicles(
        page: Int = 1
    ): Flow<Resource<List<VehicleItem>>>

}