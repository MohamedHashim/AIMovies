package com.example.aimovies.data.local.favourite

import example.moviedb.FavouriteEntitiy
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 24/07/2023.
 */
interface FavouriteMovieDataSource {
    fun getFavouriteMovies(): Flow<List<FavouriteEntitiy>>

    suspend fun getFavouriteMovie(movieId: Long): FavouriteEntitiy?

    suspend fun insertFavouriteMovie(
        movieId: Long,
        title: String,
        overview: String,
        posterPath: String,
        voteAverage: String,
        releaseDate: String
    )

    suspend fun deleteFavouriteMovie(movieId: Long)
}