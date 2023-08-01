package com.example.aimovies.data.local.rating

import com.example.aimovies.MovieDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import example.moviedb.MovieRateEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by A.Elkhami on 01/08/2023.
 */
class MovieRatingDataSourceImpl(
    db: MovieDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : MovieRatingDataSource {
    private val queries = db.movieRateEntitiyQueries
    override fun getAllMovieRatings(): Flow<List<MovieRateEntity>> {
        return queries.getAllMovieRatings().asFlow().mapToList()
    }

    override suspend fun getMovieRating(movieId: Long): MovieRateEntity? {
        return queries.getMovieRating(movieId).executeAsOneOrNull()
    }

    override suspend fun insertMovieRating(movieId: Long, rating: Float) {
        withContext(ioDispatcher) {
            queries.insertMovieRating(id = null, movieId = movieId, rate = rating.toDouble())

        }
    }

    override suspend fun updateMovieRating(movieId: Long, rating: Float) {
        withContext(ioDispatcher) {
            queries.updateMovieRating(movieId = movieId, rate = rating.toDouble())
        }
    }

}