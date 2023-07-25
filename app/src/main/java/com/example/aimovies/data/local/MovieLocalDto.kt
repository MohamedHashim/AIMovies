package com.example.aimovies.data.local

/**
 * Created by A.Elkhami on 25/07/2023.
 */
data class MovieLocalDto(
    val id: Long? = null,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: String,
    val releaseDate: String
)
