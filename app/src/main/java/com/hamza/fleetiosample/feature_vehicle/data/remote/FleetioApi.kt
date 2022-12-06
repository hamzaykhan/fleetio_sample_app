package com.hamza.fleetiosample.feature_vehicle.data.remote

import com.hamza.fleetiosample.feature_vehicle.data.remote.dto.CommentDto
import com.hamza.fleetiosample.feature_vehicle.data.remote.dto.VehicleDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface FleetioApi {

    @GET("vehicles")
    suspend fun getVehicles(
        @Query("page") page: Int,
        @Query("q[name_matches]") name: String,
        @Query("q[color_eq]") color: String,
        @Query("q[year_gteq]") year: Int,
        @Query("q[secondary_meter_true]") secondaryMeter: Int,
        @Query("q[s]") sort: String
    ): List<VehicleDto>

    @GET("comments")
    suspend fun getComments(
        @Query("page") page: Int,
        @Query("q[s]") sort: String
    ): List<CommentDto>
}