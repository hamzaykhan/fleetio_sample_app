package com.hamza.fleetiosample.feature_vehicle.data.remote.dto

data class CommentDto(
    val comment: String?,
    val commentable_id: Int?,
    val commentable_type: String?,
    val created_at: String?,
    val id: Int?,
    val rich_content: RichContent?,
    val title: String?,
    val updated_at: String?,
    val user_full_name: String?,
    val user_id: Int?,
    val user_image_url: String?,
    val with_mentions: Boolean?
)

data class RichContent(
    val state: List<State>?
)

data class State(
    val children: List<Children>?,
    val type: String?
)

data class Children(
    val children: List<ChildrenX>?,
    val data: Data?,
    val text: String?,
    val type: String?
)

data class ChildrenX(
    val text: String?
)

data class Data(
    val can_watch: Boolean?,
    val contact: Contact?,
    val label: String?,
    val record_id: Int?,
    val record_type: String?
)

data class Contact(
    val default_image: DefaultImage?,
    val email: String?,
    val full_name: String?,
    val id: Int?
)

data class DefaultImage(
    val secure_file_url: String?,
    val small_square_url: String?
)