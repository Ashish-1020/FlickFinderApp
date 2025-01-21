package com.example.flickfinder.data
data class DetailsResponse(
    val id: Int,
    val title: String,
    val runtime_minutes:Int,
    val plot_overview: String,
    val type: String,
    val year: Int,
    val release_date: String?,
    val genres: List<Int>,
    val genre_names: List<String>,
    val user_rating: Double,
    val critic_score:Int,
    val poster: String,
    val trailer: String?,
    val sources: List<Source>,
    val network_names: List<String>
)

data class Source(
    val name: String,
    val type: String,
    val region: String,
    val web_url: String
)
