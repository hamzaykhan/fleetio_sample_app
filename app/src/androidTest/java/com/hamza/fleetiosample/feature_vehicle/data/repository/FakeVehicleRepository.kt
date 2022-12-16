package com.hamza.fleetiosample.feature_vehicle.data.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toVehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.repository.VehicleRepository
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeVehicleRepository(
    private val database: FleetioDatabase
): VehicleRepository {

    override suspend fun getVehicles(
        page: Int,
        filter: VehicleFilter,
        fetchRemote: Boolean
    ): Flow<Resource<List<VehicleItem>>>  = flow {
        val vehicles = database.vehicleDao.getVehicles(
            page = page,
            name = filter.name,
            color = filter.color,
            year = filter.year,
            secondaryMeter = filter.getSecondaryMeter(),
            sortQuery = filter.sortFormattedLocalString()
        )
        if (vehicles.isEmpty())
            emit(Resource.Empty)
        else
            emit(Resource.Success(
                vehicles.map { it.toVehicleItem() }
            ))
    }

    override suspend fun insertVehicles(vehicles: List<VehicleWithLocation>) {
        database.vehicleDao.insertVehicles(vehicles)
    }

    override suspend fun getLocalVehicles(page: Int, filter: VehicleFilter): List<VehicleItem> {
        val vehicles = database.vehicleDao.getVehicles(
            page = page,
            name = filter.name,
            color = filter.color,
            year = filter.year,
            secondaryMeter = filter.getSecondaryMeter(),
            sortQuery = filter.sortFormattedLocalString()
        )
        return vehicles.map { it.toVehicleItem() }
    }
}