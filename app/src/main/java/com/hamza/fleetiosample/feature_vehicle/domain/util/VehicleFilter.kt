package com.hamza.fleetiosample.feature_vehicle.domain.util

import com.hamza.fleetiosample.feature_vehicle.presentation.listing.filter.SortOrder

data class VehicleFilter(
    val name: String = "",
    val color: String = "",
    val year: Float = 1990f,
    val secondaryMeter: Boolean = false,
    var sort: List<SortOrder> = emptyList()
) {

    fun sortFormattedString(): String {
        return buildString {
            if (sort.isEmpty()) ""
            else
                sort.forEachIndexed { index, sortOrder ->
                    when (sortOrder) {
                        SortOrder.NAME -> append("name+asc")
                        SortOrder.YEAR -> append("year+desc")
                        SortOrder.DATE -> append("created_at+desc")
                    }
                    if (index < sort.size - 1)
                        append(",")
                }
        }
    }
}
