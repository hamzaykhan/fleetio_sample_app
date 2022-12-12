package com.hamza.fleetiosample.feature_vehicle.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.LocationEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleSpecEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational.VehicleWithLocation
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter

@Dao
interface VehicleDao {

    suspend fun insertVehicles(
        vehicleEntities: List<VehicleWithLocation>
    ) {
        vehicleEntities.forEach {
            val vehicle = getVehicleById(it.vehicle.id)
            if (vehicle == null) {
                insertVehicle(it.vehicle)
                insertLocation(it.location)
                insertVehicleSpec(it.spec)
            }
            // else update vehicle and location fields
        }
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(
        vehicleEntity: VehicleEntity
    )

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(
        locationEntity: LocationEntity
    )

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicleSpec(
        vehicleSpec: VehicleSpecEntity
    )

    @Transaction
    @Query(
        """
            SELECT * 
            FROM vehicleentity
        """
    )
    suspend fun getVehicles(): List<VehicleWithLocation>

    @Transaction
    @Query(
        """
            SELECT * 
            FROM vehicleentity
            WHERE id == :id
        """
    )
    suspend fun getVehicleById(id: Int): VehicleWithLocation?

    @Transaction
    @Query(
        """
            SELECT * 
            FROM vehicleentity
            WHERE page == :page AND 
            year >= :year AND 
            secondaryMeter == :secondaryMeter AND 
            LOWER(name) LIKE '%' || LOWER(:name) || '%' AND
            LOWER(color) LIKE '%' || LOWER(:color) || '%'
            ORDER BY :sortQuery
        """
    )
    suspend fun getVehicles(page: Int,
                            name: String,
                            color: String,
                            year: Float,
                            secondaryMeter: Int,
                            sortQuery: String
    ): List<VehicleWithLocation>

}