package com.example.aimovies.domain.model

import kotlinx.serialization.Serializable

/**
 * Created by A.Elkhami on 18/07/2023.
 */
@Serializable
data class MovieModel(
    val movieId: Long? = null,
    val overview: String = "",
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val voteAverage: Double? = null
)
