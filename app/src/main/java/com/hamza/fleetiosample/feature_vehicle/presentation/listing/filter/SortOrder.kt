package com.hamza.fleetiosample.feature_vehicle.presentation.listing.filter

enum class SortOrder(val value: String) {
    NAME("Name"),
    DATE("Date"),
    YEAR("Year")
}

fun getAllSortOrder(): List<SortOrder> {
    return listOf(SortOrder.NAME, SortOrder.DATE, SortOrder.YEAR)
}

fun getSortOrder(value: String): SortOrder? {
    val map = SortOrder.values().associateBy(SortOrder::value)
    return map[value]
}