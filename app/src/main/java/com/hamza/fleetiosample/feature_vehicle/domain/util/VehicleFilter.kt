package com.hamza.fleetiosample.feature_vehicle.domain.util

data class VehicleFilter(
    val page: Int = 1,
    val name: String = "",
    val color: String = "",
    val year: Int = 1990,
    val secondaryMeter: Int = 0,
    var sort: String = ""
) {

    fun updateSort(selection: String) {
        sort = selection
    }

}
