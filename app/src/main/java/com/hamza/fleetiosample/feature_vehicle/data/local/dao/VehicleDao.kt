package com.hamza.fleetiosample.feature_vehicle.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.LocationEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.relational.VehicleWithLocation

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
            WHERE page == :page
        """
    )
    suspend fun getVehicles(page: Int): List<VehicleWithLocation>

}