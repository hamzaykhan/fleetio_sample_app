package com.hamza.fleetiosample.feature_vehicle.domain.use_case

import com.hamza.fleetiosample.common.repository.FleetioRepository
import javax.inject.Inject

class UpdateVehicleData @Inject constructor(
    private val repository: FleetioRepository
) {
}