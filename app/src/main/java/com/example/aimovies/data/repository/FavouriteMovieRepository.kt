package com.example.aimovies.data.repository

import com.example.aimovies.data.local.MovieLocalDto
import example.moviedb.FavouriteEntitiy
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 25/07/2023.
 */
interface FavouriteMovieRepository {
    fun getFavouriteMovies(): Flow<List<FavouriteEntitiy>>

    suspend fun insertFavouriteMovie(
        movie: MovieLocalDto
    )

    suspend fun deleteFavouriteMovie(id: Long)
}