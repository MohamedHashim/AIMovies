package com.example.aimovies.data.remote.dto.movie_details

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int?,
    val name: String?
)