package com.hamza.fleetiosample.feature_vehicle.data.mapper

import com.hamza.fleetiosample.feature_vehicle.data.local.entity.CommentEntity
import com.hamza.fleetiosample.feature_vehicle.data.remote.dto.CommentDto
import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem
import kotlin.random.Random

fun CommentDto.toCommentItem(page: Int): CommentItem {

    return CommentItem(
        id= id ?: Random.nextInt(),
        title = title ?: "",
        userName = user_full_name ?: "",
        userImage = user_image_url ?: "",
        comment = comment ?: "",
        updatedAt = updated_at.toLocalDateTime(),
        createdAt = created_at.toLocalDateTime(),
        page = page
    )

}

fun CommentItem.toCommentEntity(): CommentEntity {

    return CommentEntity(
        id= id,
        title = title,
        userName = userName,
        userImage = userImage,
        comment = comment,
        updatedAt = updatedAt,
        createdAt = createdAt,
        page = page
    )

}

fun CommentEntity.toCommentItem(): CommentItem {

    return CommentItem(
        id= id,
        title = title,
        userName = userName,
        userImage = userImage,
        comment = comment,
        updatedAt = updatedAt,
        createdAt = createdAt,
        page = page
    )

}