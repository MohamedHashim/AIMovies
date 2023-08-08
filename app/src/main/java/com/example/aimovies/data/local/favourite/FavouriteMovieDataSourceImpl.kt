package com.example.aimovies.data.local.favourite

import com.example.aimovies.MovieDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import example.moviedb.FavouriteEntitiy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by A.Elkhami on 24/07/2023.
 */
class FavouriteMovieDataSourceImpl(
    db: MovieDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : FavouriteMovieDataSource {

    private val queries = db.favouriteEntitiyQueries

    override fun getFavouriteMovies(): Flow<List<FavouriteEntitiy>> {
        return queries.getFavouriteMovies().asFlow().mapToList()
    }

    override suspend fun getFavouriteMovie(movieId: Long): FavouriteEntitiy? {
        return queries.getFavouriteMovie(movieId).executeAsOneOrNull()
    }

    override suspend fun insertFavouriteMovie(
        movieId: Long,
        title: String,
        overview: String,
        posterPath: String,
        voteAverage: String,
        releaseDate: String
    ) {
        withContext(ioDispatcher) {
            queries.insertFavouriteMovie(
                id = null,
                movieId = movieId,
                title = title,
                overview = overview,
                posterPath = posterPath,
                voteAverage = voteAverage,
                releaseDate = releaseDate
            )
        }
    }

    override suspend fun deleteFavouriteMovie(movieId: Long) {
        withContext(ioDispatcher) {
            queries.deleteFavouriteMovie(movieId)
        }
    }
}