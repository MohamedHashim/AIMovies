package com.example.aimovies.data.repository.favourite

import com.example.aimovies.data.local.FavouriteMovieDataSource
import com.example.aimovies.data.local.dto.MovieLocal
import example.moviedb.FavouriteEntitiy
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 25/07/2023.
 */
class FavouriteMovieRepositoryImpl(private val dataSource: FavouriteMovieDataSource) :
    FavouriteMovieRepository {
    override fun getFavouriteMovies(): Flow<List<FavouriteEntitiy>> {
        return dataSource.getFavouriteMovies()
    }

    override suspend fun getFavouriteMovie(title: String): FavouriteEntitiy? {
        return dataSource.getFavouriteMovie(title)
    }

    override suspend fun insertFavouriteMovie(movie: MovieLocal) {
        dataSource.insertFavouriteMovie(
            movie.id,
            movie.title,
            movie.overview,
            movie.posterPath,
            movie.voteAverage,
            movie.releaseDate
        )
    }

    override suspend fun deleteFavouriteMovie(id: Long) {
        dataSource.deleteFavouriteMovie(id)
    }
}