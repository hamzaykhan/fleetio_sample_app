package com.hamza.fleetiosample.feature_vehicle.presentation.listing.filter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroup(
    sortOrders: List<SortOrder> = getAllSortOrder(),
    selectedSortOrders: List<SortOrder> = listOf(),
    onSelectedChanged: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(
            paddingValues = PaddingValues(vertical = 8.dp)
        )
    ) {
        LazyRow {
            items(sortOrders.size) { i ->
                val sort = sortOrders[i]
                Chip(
                    name = sort.value,
                    isSelected = selectedSortOrders.contains(sort),
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}