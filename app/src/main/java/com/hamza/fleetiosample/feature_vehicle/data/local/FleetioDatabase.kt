package com.hamza.fleetiosample.feature_vehicle.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamza.fleetiosample.feature_vehicle.data.local.dao.VehicleDao
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleEntity

@Database(
    entities = [VehicleEntity::class],
    version = 1
)
abstract class FleetioDatabase: RoomDatabase() {

    abstract val vehicleDao: VehicleDao

    companion object {
        const val DATABASE_NAME = "fleetio_database"
    }
}