package com.hamza.fleetiosample.feature_vehicle.data.repository

import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.CommentEntity
import com.hamza.fleetiosample.feature_vehicle.data.mapper.toCommentItem
import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem
import com.hamza.fleetiosample.feature_vehicle.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCommentRepository(
    private val database: FleetioDatabase
): CommentRepository {

    override suspend fun getComments(
        page: Int,
        sort: String,
        fetchServer: Boolean
    ): Flow<Resource<List<CommentItem>>> = flow {
        val comments = database
            .commentDao
            .getComments(page = page)

        if (comments.isEmpty())
            emit(Resource.Empty)
        else
            emit(Resource.Success(
                comments.map { it.toCommentItem() }
            ))
    }

    override suspend fun insertComments(comments: List<CommentEntity>) {
        database.commentDao.insertComment(comments)
    }

    override suspend fun getLocalComments(page: Int, sort: String): List<CommentItem> {
        return database
            .commentDao
            .getComments(page = page).map {
                it.toCommentItem()
            }
    }
}