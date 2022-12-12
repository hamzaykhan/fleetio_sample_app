package com.hamza.fleetiosample.feature_vehicle.data.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.common.wrapper.callApi
import com.hamza.fleetiosample.common.wrapper.data
import com.hamza.fleetiosample.common.wrapper.succeeded
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.CommentEntity
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toCommentEntity
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toCommentItem
import com.hamza.fleetiosample.feature_vehicle.data.remote.FleetioApi
import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem
import com.hamza.fleetiosample.feature_vehicle.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val apiService: FleetioApi,
    private val database: FleetioDatabase
): CommentRepository {

    override suspend fun getComments(
        page: Int,
        sort: String,
        fetchServer: Boolean
    ): Flow<Resource<List<CommentItem>>> = flow {
        emit(Resource.Loading)

        val localComments = getLocalComments(page = page, sort = sort)

        val shouldLoadFromServer = localComments.isEmpty() || fetchServer

        if (!shouldLoadFromServer) {
            return@flow emit(Resource.Success(localComments))
        }

        val result = callApi {
            apiService.getComments(
                page = page,
                sort = sort
            ).map { it.toCommentItem(page = page) }
        }

        if (result.succeeded()) {
            insertComments(comments = result.data!!
                .map { it.toCommentEntity() })
            emit(Resource.Success(
                getLocalComments(
                    page = page,
                    sort = sort
                )
            ))
        } else {
            emit(result)
        }
    }

    override suspend fun insertComments(comments: List<CommentEntity>) {
        database.commentDao.insertComment(comments)
    }

    override suspend fun getLocalComments(
        page: Int,
        sort: String
    ): List<CommentItem> {
        return database.commentDao
            .getComments(page = page)
            .map {
                it.toCommentItem()
            }
    }

}