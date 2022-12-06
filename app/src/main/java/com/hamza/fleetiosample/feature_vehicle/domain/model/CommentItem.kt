package com.hamza.fleetiosample.feature_vehicle.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class CommentItem(
    val id: Int,
    val title: String,
    val userName: String,
    val userImage: String,
    val comment: String,
    val updatedAt: LocalDateTime?,
    val createdAt: LocalDateTime?,
    val page: Int
) : Parcelable
