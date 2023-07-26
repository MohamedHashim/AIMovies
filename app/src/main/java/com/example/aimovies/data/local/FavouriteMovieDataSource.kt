package com.example.aimovies.data.local

import example.moviedb.FavouriteEntitiy
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 24/07/2023.
 */
interface FavouriteMovieDataSource {
    fun getFavouriteMovies(): Flow<List<FavouriteEntitiy>>

    suspend fun getFavouriteMovie(title: String): FavouriteEntitiy?

    suspend fun insertFavouriteMovie(
        id: Long? = null,
        title: String,
        overview: String,
        posterPath: String,
        voteAverage: String,
        releaseDate: String
    )

    suspend fun deleteFavouriteMovie(id: Long)
}