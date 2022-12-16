package com.hamza.fleetiosample.feature_vehicle.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hamza.fleetiosample.feature_vehicle.data.local.converter.LocalDateTimeConverter
import com.hamza.fleetiosample.feature_vehicle.data.local.dao.CommentDao
import com.hamza.fleetiosample.feature_vehicle.data.local.dao.VehicleDao
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.CommentEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.LocationEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleEntity
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.VehicleSpecEntity

@Database(
    entities = [VehicleEntity::class,
        LocationEntity::class,
        CommentEntity::class,
        VehicleSpecEntity::class],
    version = 1
)
@TypeConverters(
    LocalDateTimeConverter::class
)
abstract class FleetioDatabase: RoomDatabase() {

    abstract val vehicleDao: VehicleDao
    abstract val commentDao: CommentDao

    companion object {
        const val DATABASE_NAME = "fleetio_database"
    }
}