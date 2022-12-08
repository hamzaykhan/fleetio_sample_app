package com.hamza.fleetiosample.feature_vehicle.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationItem(
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val country: String,
    val countryShort: String,
    val postalCode: String,
    val region: String,
    val regionShort: String,
    val street: String,
    val streetNumber: String
): Parcelable {

    fun getFormattedAddress(): String {
        var address = buildString {
            if (streetNumber.isNotBlank())
                append("$streetNumber, ")

            if (street.isNotBlank())
                append("$street, ")

            if (region.isNotBlank())
                append("$region, ")

            if (regionShort.isNotBlank())
                append("$regionShort, ")

            if (postalCode.isNotBlank())
                append("$postalCode, ")

            if (city.isNotBlank())
                append("$city, ")

            if (country.isNotBlank())
                append("$country, ")

            if (countryShort.isNotBlank())
                append("$countryShort, ")
        }
        return address.removeSuffix(", ")
    }
}