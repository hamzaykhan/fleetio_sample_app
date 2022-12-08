package com.hamza.fleetiosample.feature_vehicle.data.mapper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun String?.toLocalDateTime(): LocalDateTime? {
    // 2022-05-12T11:54:38.407-07:00
    return this?.let {

        val pattern = "yyyy-MM-dd'T'HH:mm:ss"
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
        LocalDateTime.parse(it.split(".")[0], formatter)
    } ?: null
}

fun LocalDateTime?.toFormattedString(): String {
    return this?.let {
        val pattern = "MMMM dd, yyyy"
        return it?.format(DateTimeFormatter.ofPattern(pattern))
    } ?: "N/A"
}