package com.hamza.fleetiosample.feature_vehicle.presentation.info

import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem

data class VehicleInfoState(
    val vehicle: VehicleItem? = null,
    val commentItems: List<CommentItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
