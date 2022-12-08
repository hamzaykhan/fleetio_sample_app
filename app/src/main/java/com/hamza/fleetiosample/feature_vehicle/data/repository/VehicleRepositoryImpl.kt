package com.hamza.fleetiosample.feature_vehicle.data.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.common.wrapper.callApi
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toVehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toVehicleItem
import com.hamza.fleetiosample.feature_vehicle.data.remote.FleetioApi
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.repository.VehicleRepository
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter
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
        filter: VehicleFilter,
        fetchLocal: Boolean
    ): Flow<Resource<List<VehicleItem>>>  = flow {
        emit(Resource.Loading)

        if (fetchLocal)
            getLocalVehicles(page = page)

        val result = callApi {
            val data = apiService.getVehicles(
                page = page,
                name = filter.name,
                color = filter.color,
                year = filter.year.toInt(),
                secondaryMeter = if (filter.secondaryMeter) 1 else 0,
                sort = filter.sortFormattedString()
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
        page: Int
    ): Flow<Resource<List<VehicleItem>>>  = flow {
        val data = database.vehicleDao.getVehicles(page).map { it.toVehicleItem() }

        if (data.isNotEmpty())
            emit(Resource.Success(data))
    }

}