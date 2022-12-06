package com.hamza.fleetiosample.feature_vehicle.data.mapper

import com.hamza.fleetiosample.feature_vehicle.data.local.entity.LocationEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.data.remote.dto.VehicleDto
import com.hamza.fleetiosample.feature_vehicle.domain.model.LocationItem
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import kotlin.random.Random

fun VehicleDto.toVehicleItem(page: Int): VehicleItem  {

    return VehicleItem(
        id = id ?: Random.nextInt(),
        name = name ?: "",
        defaultUrl = default_image_url ?: "",
        make = make ?: "",
        model = model ?: "",
        meterName = meter_name ?: "",
        year = year ?: -1,
        vin = vin ?: "",
        licensePlate = license_plate ?: "",
        location = LocationItem(
            latitude = current_location_entry?.geolocation?.latitude ?: 0.0,
            longitude = current_location_entry?.geolocation?.longitude ?: 0.0,
        ),
        page = page
    )

}

fun VehicleItem.toVehicleEntity(): VehicleWithLocation {

    return VehicleWithLocation(
        vehicle = VehicleEntity(
            id = id,
            name = name,
            defaultUrl = defaultUrl,
            make = make,
            model = model,
            meterName = meterName,
            year = year,
            vin = vin,
            licensePlate = licensePlate,
            page = page
        ),
        location = LocationEntity(
            vehicleId = id,
            latitude = location.latitude,
            longitude = location.longitude
        )
    )

}

fun VehicleWithLocation.toVehicleItem(): VehicleItem  {

    return VehicleItem(
        id = vehicle.id,
        name = vehicle.name,
        defaultUrl = vehicle.defaultUrl,
        make = vehicle.make,
        model = vehicle.model,
        meterName = vehicle.meterName,
        year = vehicle.year,
        vin = vehicle.vin,
        licensePlate = vehicle.licensePlate,
        page = vehicle.page,
        location = LocationItem(
            latitude = location.latitude,
            longitude = location.longitude,
        )
    )

}