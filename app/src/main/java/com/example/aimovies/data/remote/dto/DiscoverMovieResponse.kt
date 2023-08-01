package com.example.aimovies.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DiscoverMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)