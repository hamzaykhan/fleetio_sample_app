package com.hamza.fleetiosample.feature_vehicle.data.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.common.wrapper.callApi
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.CommentEntity
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toCommentEntity
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toCommentItem
import com.hamza.fleetiosample.feature_vehicle.data.remote.FleetioApi
import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem
import com.hamza.fleetiosample.feature_vehicle.domain.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val apiService: FleetioApi,
    private val database: FleetioDatabase
): CommentRepository {

    override suspend fun getComments(page: Int, sort: String): Flow<Resource<List<CommentItem>>> = flow {
        emit(Resource.Loading)

        getLocalComments(page = page, sort = sort)

        val result = callApi {
            val data = apiService.getComments(
                page = page,
                sort = sort
            ).map { it.toCommentItem(page = page) }
            insertComments(data.map { it.toCommentEntity() })
            data
        }

        emit(result)
    }

    override suspend fun insertComments(comments: List<CommentEntity>) {
        database.commentDao.insertComment(comments)
    }

    override suspend fun getLocalComments(
        page: Int,
        sort: String
    ): Flow<Resource<List<CommentItem>>>  = flow {
        val data =  database.commentDao
            .getComments(page = page)
            .map { it.toCommentItem() }

        if (data.isNotEmpty())
            emit(Resource.Success(data))
    }

}