package com.example.flickfinder.data
data class WatchmodeResponse(
    val releases: List<Release>
)

data class Release(
    val id: Int,
    val title: String,
    val type: String,
    val poster_url: String
)
