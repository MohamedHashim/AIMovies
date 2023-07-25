package com.example.aimovies.domain.model

/**
 * Created by A.Elkhami on 18/07/2023.
 */
data class MovieModel(
    val id: Long? = null,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double
)
