package com.example.aimovies.data.repository.rating

import com.example.aimovies.data.local.rating.MovieRatingDataSource
import example.moviedb.MovieRateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 01/08/2023.
 */
class MovieRatingRepositoryImpl(val dataSource: MovieRatingDataSource) : MovieRatingRepository {
    override fun getAllMovieRatings(): Flow<List<MovieRateEntity>> {
        return dataSource.getAllMovieRatings()
    }

    override suspend fun getMovieRating(movieId: Long): MovieRateEntity? {
        return dataSource.getMovieRating(movieId)
    }

    override suspend fun insertMovieRating(movieId: Long, rating: Float) {
        dataSource.insertMovieRating(movieId, rating)
    }

    override suspend fun updateMovieRating(movieId: Long, rating: Float) {
        dataSource.updateMovieRating(movieId, rating)
    }

}