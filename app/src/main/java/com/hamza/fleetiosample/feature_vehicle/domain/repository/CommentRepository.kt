package com.hamza.fleetiosample.feature_vehicle.domain.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.CommentEntity
import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    suspend fun getComments(
        page: Int,
        sort: String,
        fetchServer: Boolean
    ): Flow<Resource<List<CommentItem>>>

    suspend fun insertComments(comments: List<CommentEntity>)

    suspend fun getLocalComments(
        page: Int,
        sort: String
    ): List<CommentItem>

}