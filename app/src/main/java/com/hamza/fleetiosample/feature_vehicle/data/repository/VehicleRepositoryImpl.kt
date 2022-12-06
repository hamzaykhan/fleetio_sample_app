package com.hamza.fleetiosample.feature_vehicle.data.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.common.wrapper.callApi
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.local.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toVehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toVehicleItem
import com.hamza.fleetiosample.feature_vehicle.data.remote.FleetioApi
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val apiService: FleetioApi,
    private val database: FleetioDatabase
): VehicleRepository {

    override suspend fun getVehicles(
        page: Int,
        name: String,
        color: String,
        year: Int,
        secondaryMeter: Int,
        sort: String
    ): Flow<Resource<List<VehicleItem>>> = flow {
        emit(Resource.Loading)

        getLocalVehicles(page = page)

        val result = callApi {
            val data = apiService.getVehicles(
                page = page,
                name = name,
                color = color,
                year = year,
                secondaryMeter = secondaryMeter,
                sort = sort
            ).map { it.toVehicleItem(page = page) }

            insertVehicles(data.map { it.toVehicleEntity() })

            data
        }

        emit(result)
    }

    override suspend fun insertVehicles(vehicles: List<VehicleWithLocation>) {
        database.vehicleDao.insertVehicles(vehicles)
    }

    override suspend fun getLocalVehicles(
        page: Int,
        name: String,
        color: String,
        year: Int,
        secondaryMeter: Int,
        sort: String
    ): Flow<Resource<List<VehicleItem>>>  = flow {
        val data = database.vehicleDao.getVehicles(page).map { it.toVehicleItem() }

        if (data.isNotEmpty())
            emit(Resource.Success(data))
    }

}