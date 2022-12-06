package com.hamza.fleetiosample.di

import com.hamza.fleetiosample.common.repository.FleetioRepository
import com.hamza.fleetiosample.common.repository.FleetioRepositoryImpl
import com.hamza.fleetiosample.feature_vehicle.data.repository.CommentRepositoryImpl
import com.hamza.fleetiosample.feature_vehicle.data.repository.VehicleRepositoryImpl
import com.hamza.fleetiosample.feature_vehicle.domain.repository.CommentRepository
import com.hamza.fleetiosample.feature_vehicle.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCommentRepo(
        commentRepo: CommentRepositoryImpl
    ): CommentRepository

    @Binds
    @Singleton
    abstract fun bindVehicleRepo(
        vehicleRepo: VehicleRepositoryImpl
    ): VehicleRepository

    @Binds
    @Singleton
    abstract fun bindFleetioRepo(
        fleetioRepo: FleetioRepositoryImpl
    ): FleetioRepository

}