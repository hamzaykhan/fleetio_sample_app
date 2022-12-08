package com.hamza.fleetiosample.feature_vehicle.data.mapper

import com.hamza.fleetiosample.feature_vehicle.data.local.entity.LocationEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleSpecEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.data.remote.dto.VehicleDto
import com.hamza.fleetiosample.feature_vehicle.domain.model.LocationItem
import com.hamza.fleetiosample.feature_vehicle.domain.model.Specs
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
            city = current_location_entry?.address_components?.city ?: "",
            country = current_location_entry?.address_components?.country ?: "",
            countryShort = current_location_entry?.address_components?.country_short ?: "",
            postalCode = current_location_entry?.address_components?.postal_code ?: "",
            region = current_location_entry?.address_components?.region ?: "",
            regionShort = current_location_entry?.address_components?.region_short ?: "",
            street = current_location_entry?.address_components?.street ?: "",
            streetNumber = current_location_entry?.address_components?.street_number ?: ""
        ),
        specs = Specs(
            engineBrand = specs?.engine_brand ?: "",
            cargoVolume = specs?.cargo_volume ?: 0.0,
            engineStrokes = specs?.engine_stroke ?: 0.0,
            engineDescription = specs?.engine_description ?: "",
            fuelTankCapacity = specs?.fuel_tank_capacity ?: 0.0,
            transmissionGear = specs?.transmission_gears ?: 0
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
            longitude = location.longitude,
            city = location.city,
            country = location.country,
            countryShort = location.countryShort,
            postalCode = location.postalCode,
            region = location.region,
            regionShort = location.regionShort,
            street = location.street,
            streetNumber = location.streetNumber
        ),
        spec = VehicleSpecEntity(
            vehicleId = id,
            engineBrand = specs.engineBrand,
            cargoVolume = specs.cargoVolume,
            engineStrokes = specs.engineStrokes,
            engineDescription = specs.engineDescription,
            fuelTankCapacity = specs.fuelTankCapacity,
            transmissionGear = specs.transmissionGear
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
            city = location.city,
            country = location.country,
            countryShort = location.countryShort,
            postalCode = location.postalCode,
            region = location.region,
            regionShort = location.regionShort,
            street = location.street,
            streetNumber = location.streetNumber
        ),
        specs = Specs(
            engineBrand = spec.engineBrand,
            cargoVolume = spec.cargoVolume,
            engineStrokes = spec.engineStrokes,
            engineDescription = spec.engineDescription,
            fuelTankCapacity = spec.fuelTankCapacity,
            transmissionGear = spec.transmissionGear
        )
    )

}