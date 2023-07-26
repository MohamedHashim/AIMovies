package com.example.aimovies.data.local.dto

/**
 * Created by A.Elkhami on 25/07/2023.
 */
data class MovieLocal(
    val id: Long? = null,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: String,
    val releaseDate: String
)
