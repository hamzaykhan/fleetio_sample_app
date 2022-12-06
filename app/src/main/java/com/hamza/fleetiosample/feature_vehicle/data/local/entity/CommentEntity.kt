package com.hamza.fleetiosample.feature_vehicle.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class CommentEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val userName: String,
    val userImage: String,
    val comment: String,
    val updatedAt: LocalDateTime?,
    val createdAt: LocalDateTime?,
    val page: Int
)
