package com.hamza.fleetiosample.feature_vehicle.domain.util

import com.hamza.fleetiosample.feature_vehicle.presentation.listing.filter.SortOrder

data class VehicleFilter(
    val name: String = "",
    val color: String = "",
    val year: Float = 1990f,
    val secondaryMeter: Boolean = false,
    var sort: List<SortOrder> = emptyList()
) {

    fun getSecondaryMeter(): Int {
        return if (secondaryMeter) 1 else 0
    }

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

    fun sortFormattedLocalString(): String {
        return buildString {
            if (sort.isEmpty()) ""
            else
                sort.forEachIndexed { index, sortOrder ->
                    when (sortOrder) {
                        SortOrder.NAME -> append("name ASC")
                        SortOrder.YEAR -> append("year DESC")
                        SortOrder.DATE -> append("createdAt DESC")
                    }
                    if (index < sort.size - 1)
                        append(",")
                }
        }
    }
}
