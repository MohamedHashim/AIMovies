package com.example.aimovies.domain.mapper

import com.example.aimovies.data.local.MovieLocalDto
import com.example.aimovies.data.remote.dto.Movie
import com.example.aimovies.domain.model.MovieModel

/**
 * Created by A.Elkhami on 18/07/2023.
 */
fun Movie.toMovieModel() = MovieModel(
    title = title,
    overview = overview,
    releaseDate = release_date,
    posterPath = poster_path,
    voteAverage = vote_average
)

fun MovieModel.toMovieLocalDto() = MovieLocalDto(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    voteAverage = voteAverage.toString()
)