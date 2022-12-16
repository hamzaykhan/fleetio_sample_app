package com.hamza.fleetiosample.feature_vehicle.domain.use_case

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.hamza.fleetiosample.common.wrapper.data
import com.hamza.fleetiosample.common.wrapper.succeeded
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toVehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.repository.FakeFleetioRepository
import com.hamza.fleetiosample.feature_vehicle.domain.model.LocationItem
import com.hamza.fleetiosample.feature_vehicle.domain.model.Specs
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter
import com.hamza.fleetiosample.feature_vehicle.presentation.listing.filter.SortOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.random.Random

class GetVehiclesTest {

    private lateinit var getVehicles: GetVehicles
    private lateinit var repository: FakeFleetioRepository
    private lateinit var database: FleetioDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FleetioDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = FakeFleetioRepository(database)

        getVehicles = GetVehicles(repository)

        val colorList = listOf("Red", "Yellow", "Green", "Black", "White")

        val vehiclesToInsert = mutableListOf<VehicleItem>()
        ('a'..'z').forEachIndexed { index, c ->
            vehiclesToInsert.add(
                VehicleItem(
                    id = index,
                    name = c.toString(),
                    defaultUrl = c.toString(),
                    make = c.toString(),
                    model = c.toString(),
                    meterName = c.toString(),
                    year = Random.nextInt(2022),
                    vin = c.toString(),
                    licensePlate = c.toString(),
                    location = LocationItem(
                        latitude = index.toDouble(),
                        longitude = index.toDouble(),
                        city = c.toString(),
                        country = c.toString(),
                        countryShort = c.toString(),
                        postalCode = c.toString(),
                        region = c.toString(),
                        regionShort = c.toString(),
                        street = c.toString(),
                        streetNumber = c.toString()
                    ),
                    color = colorList.random(),
                    secondaryMeter = Random.nextBoolean(),
                    createdAt = LocalDateTime.now().minusDays(index.toLong()),
                    specs = Specs(
                        engineBrand = c.toString(),
                        cargoVolume = index.toDouble(),
                        engineStrokes = index.toDouble(),
                        engineDescription = c.toString(),
                        fuelTankCapacity = index.toDouble(),
                        transmissionGear = Random.nextInt(5)
                    ),
                    page = 1
                )
            )
        }

        vehiclesToInsert.shuffle()

        runBlocking {
            repository
                .getVehicleRepository()
                .insertVehicles(
                    vehiclesToInsert.map {
                        it.toVehicleEntity()
                    }
                )
        }
    }

    @Test
    fun getVehicles_SortByName_Ascending() = runBlocking {
        val filter = VehicleFilter(
            sort = listOf(SortOrder.NAME)
        )
        val result = getVehicles.invoke(
            page = 1,
            filter = filter,
            fetchRemote = false
        ).first()

        if (result.succeeded()) {
            val vehicles = result.data!!
            for (i in 0 until vehicles.size- 2) {
                assertThat(vehicles[i].name).isLessThan(vehicles[i+1].name)
            }
        }
    }

    @Test
    fun getVehicles_SortByDate_Descending() = runBlocking {
        val filter = VehicleFilter(
            sort = listOf(SortOrder.DATE)
        )
        val result = getVehicles.invoke(
            page = 1,
            filter = filter,
            fetchRemote = false
        ).first()

        if (result.succeeded()) {
            val vehicles = result.data!!
            for (i in 0 until vehicles.size- 2) {
                assertThat(vehicles[i].createdAt).isGreaterThan(vehicles[i+1].createdAt)
            }
        }
    }

    @Test
    fun getVehicles_SortByYear_Descending() = runBlocking {
        val filter = VehicleFilter(
            sort = listOf(SortOrder.YEAR)
        )
        val result = getVehicles.invoke(
            page = 1,
            filter = filter,
            fetchRemote = false
        ).first()

        if (result.succeeded()) {
            val vehicles = result.data!!
            for (i in 0 until vehicles.size- 2) {
                assertThat(vehicles[i].createdAt).isGreaterThan(vehicles[i+1].createdAt)
            }
        }
    }

    @Test
    fun getVehicles_ByYearColorSecondaryMeter_ReturnValidVehicles() = runBlocking {
        val filter = VehicleFilter(
            year = 2005f,
            sort = listOf(SortOrder.NAME),
            secondaryMeter = false,
            color = "red"
        )
        val result = getVehicles.invoke(
            page = 1,
            filter = filter,
            fetchRemote = false
        ).first()

        if (result.succeeded()) {
            val vehicles = result.data!!
            for (i in 0 until vehicles.size- 1) {
                assertThat(vehicles[i].year).isGreaterThan(2004)
                assertThat(vehicles[i].secondaryMeter).isFalse()

                if ((i + 1) < vehicles.size)
                    assertThat(vehicles[i].name).isLessThan(vehicles[i+1].name)
            }
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    // test cases
    // 1. Sort vehicles by name
    // 2. Sort vehicles by data
    // 3. Sort vehicles by year
    // 4. Get vehicles by year, color, secondary meter order by name
}