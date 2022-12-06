package com.hamza.fleetiosample.di

import com.hamza.fleetiosample.feature_vehicle.domain.use_case.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindVehicleUseCases(
        vehicleUseCasesImpl: VehicleUseCasesImpl
    ): VehicleUseCases

}