package com.example.flickfinder.data

data class CastCrewResponse(
    val person_id: Int,
    val type: String,
    val full_name: String,
    val headshot_url: String,
    val role: String,
    val episode_count: Int?,
    val order: Int?
)
