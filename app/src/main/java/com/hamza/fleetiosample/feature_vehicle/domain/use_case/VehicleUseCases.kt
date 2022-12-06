package com.hamza.fleetiosample.feature_vehicle.domain.use_case

import javax.inject.Inject

interface VehicleUseCases {
    fun getComments(): GetComments
    fun getVehicles(): GetVehicles
    fun updateVehicleData(): UpdateVehicleData
}

class VehicleUseCasesImpl @Inject constructor(
    private val comments: GetComments,
    private val vehicles: GetVehicles,
    private val updateVehicleData: UpdateVehicleData
): VehicleUseCases {

    override fun getComments(): GetComments {
        return comments
    }

    override fun getVehicles(): GetVehicles {
        return vehicles
    }

    override fun updateVehicleData(): UpdateVehicleData {
        return updateVehicleData
    }

}
