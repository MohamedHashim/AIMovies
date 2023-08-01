package com.example.aimovies.data.repository.favourite

import com.example.aimovies.data.local.dto.MovieLocal
import example.moviedb.FavouriteEntitiy
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 25/07/2023.
 */
interface FavouriteMovieRepository {
    fun getFavouriteMovies(): Flow<List<FavouriteEntitiy>>

    suspend fun getFavouriteMovie(title: String): FavouriteEntitiy?

    suspend fun insertFavouriteMovie(
        movie: MovieLocal
    )

    suspend fun deleteFavouriteMovie(id: Long)
}