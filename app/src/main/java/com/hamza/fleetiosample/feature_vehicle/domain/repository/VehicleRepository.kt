package com.hamza.fleetiosample.feature_vehicle.domain.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.feature_vehicle.data.local.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {

    suspend fun getVehicles(
        page: Int = 0,
        name: String = "",
        color: String = "",
        year: Int = 1990,
        secondaryMeter: Int = 0,
        sort: String = ""
    ): Flow<Resource<List<VehicleItem>>>

    suspend fun insertVehicles(vehicles: List<VehicleWithLocation>)

    suspend fun getLocalVehicles(
        page: Int = 0,
        name: String = "",
        color: String = "",
        year: Int = 1990,
        secondaryMeter: Int = 0,
        sort: String = ""
    ): Flow<Resource<List<VehicleItem>>>

}