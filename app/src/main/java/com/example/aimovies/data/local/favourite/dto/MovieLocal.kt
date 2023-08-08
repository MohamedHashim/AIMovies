package com.example.aimovies.data.local.favourite.dto

/**
 * Created by A.Elkhami on 25/07/2023.
 */
data class MovieLocal(
    val movieId: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: String,
    val releaseDate: String
)
