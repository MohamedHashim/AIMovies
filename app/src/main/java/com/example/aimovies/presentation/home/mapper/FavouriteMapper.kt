package com.example.aimovies.presentation.home.mapper

import com.example.aimovies.domain.model.MovieModel
import example.moviedb.FavouriteEntitiy

/**
 * Created by A.Elkhami on 25/07/2023.
 */

fun FavouriteEntitiy.toMovieModel(): MovieModel {
    return MovieModel(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage.toDouble(),
        releaseDate = releaseDate
    )
}