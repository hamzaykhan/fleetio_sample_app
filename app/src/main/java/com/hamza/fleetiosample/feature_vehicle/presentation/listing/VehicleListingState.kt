package com.hamza.fleetiosample.feature_vehicle.presentation.listing

import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter

data class VehicleListingState(
    val vehicles: List<VehicleItem> = emptyList(),
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val page: Int = 1,
    val endReached: Boolean = false,
    val fetchRemote: Boolean = false,
    var filters: VehicleFilter = VehicleFilter()
) {
    fun updateFilters(filters: VehicleFilter) {
        this.filters = filters
    }
}