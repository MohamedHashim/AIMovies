package com.example.aimovies.domain.mapper

import com.example.aimovies.data.remote.dto.movie_details.MovieDetailsResponse
import com.example.aimovies.domain.model.MovieModel

/**
 * Created by A.Elkhami on 29/08/2023.
 */
fun MovieDetailsResponse.toMovieModel() = MovieModel(
    movieId = id?.toLong() ?: 0,
    overview = overview ?: "",
    posterPath = posterPath ?: "",
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    voteAverage = voteAverage ?: 0.0
)