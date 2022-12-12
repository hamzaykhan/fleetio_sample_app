package com.hamza.fleetiosample.feature_vehicle.data.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.common.wrapper.callApi
import com.hamza.fleetiosample.common.wrapper.data
import com.hamza.fleetiosample.common.wrapper.succeeded
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toVehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toVehicleItem
import com.hamza.fleetiosample.feature_vehicle.data.remote.FleetioApi
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.repository.VehicleRepository
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val apiService: FleetioApi,
    private val database: FleetioDatabase
): VehicleRepository {

    override suspend fun getVehicles(
        page: Int,
        filter: VehicleFilter,
        fetchRemote: Boolean
    ): Flow<Resource<List<VehicleItem>>>  = flow {
        emit(Resource.Loading)

        val localVehicleList = getLocalVehicles(
            page = page,
            filter = filter
        )

        val loadVehicleFromServer = localVehicleList.isEmpty() || fetchRemote

        if (!loadVehicleFromServer) {
            return@flow emit(Resource.Success(localVehicleList))
        }

        val result = callApi {
            apiService.getVehicles(
                page = page,
                name = filter.name,
                color = filter.color,
                year = filter.year.toInt(),
                secondaryMeter = filter.getSecondaryMeter(),
                sort = filter.sortFormattedString()
            ).map { it.toVehicleItem(page = page) }
        }

        if (result.succeeded()) {
            val vehicleEntities = result.data!!.map { vehicleItem ->
                vehicleItem.toVehicleEntity()
            }
            insertVehicles(vehicleEntities)

            return@flow emit(Resource.Success(
                getLocalVehicles(
                    page = page,
                    filter = filter
                )
            ))
        } else {
            return@flow emit(result)
        }
    }

    override suspend fun insertVehicles(vehicles: List<VehicleWithLocation>) {
        database.vehicleDao.insertVehicles(vehicles)
    }

    override suspend fun getLocalVehicles(page: Int, filter: VehicleFilter): List<VehicleItem> {
        val localVehicleList = database.vehicleDao
            .getVehicles(
                page = page,
                name = filter.name,
                color = filter.color,
                year = filter.year,
                secondaryMeter = filter.getSecondaryMeter(),
                sortQuery = filter.sortFormattedLocalString()
            )
            .map { vehicle ->
                vehicle.toVehicleItem()
            }
        return localVehicleList
    }

}