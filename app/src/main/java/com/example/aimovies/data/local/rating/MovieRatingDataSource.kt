package com.example.aimovies.data.local.rating

import example.moviedb.MovieRateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 01/08/2023.
 */
interface MovieRatingDataSource {
    fun getAllMovieRatings(): Flow<List<MovieRateEntity>>

    suspend fun getMovieRating(movieId: Long): MovieRateEntity?

    suspend fun insertMovieRating(movieId: Long, rating: Float)

    suspend fun updateMovieRating(movieId: Long, rating: Float)
}