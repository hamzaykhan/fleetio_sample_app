package com.hamza.fleetiosample.feature_vehicle.domain.use_case

import com.hamza.fleetiosample.common.repository.FleetioRepository
import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVehicles @Inject constructor(
    private val repository: FleetioRepository
) {

    suspend operator fun invoke(
        filter: VehicleFilter,
        fetchLocal: Boolean = true
    ): Flow<Resource<List<VehicleItem>>> {
        return repository.getVehicleRepository()
            .getVehicles(
                filter = filter,
                fetchLocal = fetchLocal
            )
    }

}