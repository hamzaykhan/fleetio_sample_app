package com.hamza.fleetiosample.feature_vehicle.presentation.listing

sealed class VehicleListingEvent {
    object Refresh: VehicleListingEvent()
    data class OnSearchQueryChange(val query: String): VehicleListingEvent()
}
