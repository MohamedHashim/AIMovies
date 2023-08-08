package com.example.aimovies.data.repository.favourite

import com.example.aimovies.MovieDatabase
import com.example.aimovies.data.local.favourite.FavouriteMovieDataSourceImpl
import com.example.aimovies.stub.favouriteEntityStub
import com.example.aimovies.stub.movieLocalStub
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import example.moviedb.FavouriteEntitiy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 26/07/2023.
 */
class FavouriteMovieRepositoryTest {

    private lateinit var driver: JdbcSqliteDriver
    private lateinit var dataSource: FavouriteMovieDataSourceImpl
    private lateinit var repository: FavouriteMovieRepositoryImpl

    @Before
    fun setUp() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MovieDatabase.Schema.create(driver)

        dataSource = FavouriteMovieDataSourceImpl(MovieDatabase(driver), Dispatchers.Default)
        repository = FavouriteMovieRepositoryImpl(dataSource)
    }

    @Test
    fun getFavouriteMovies_returnFavouriteMoviesList() {
        val queries = MovieDatabase(driver).favouriteEntitiyQueries

        queries.insertFavouriteMovie(
            id = favouriteEntityStub.id,
            movieId = favouriteEntityStub.movieId,
            title = favouriteEntityStub.title,
            overview = favouriteEntityStub.overview,
            posterPath = favouriteEntityStub.posterPath,
            voteAverage = favouriteEntityStub.voteAverage,
            releaseDate = favouriteEntityStub.releaseDate
        )
        runTest {
            val expected = flow<List<FavouriteEntitiy>> {
                emit(
                    listOf(
                        favouriteEntityStub
                    )
                )
            }.first()
            val result = repository.getFavouriteMovies().first()

            assertEquals(expected, result)
        }
    }

    @Test
    fun getFavouriteMovie_returnFavouriteMovie() {
        val queries = MovieDatabase(driver).favouriteEntitiyQueries

        queries.insertFavouriteMovie(
            id = favouriteEntityStub.id,
            movieId = favouriteEntityStub.movieId,
            title = favouriteEntityStub.title,
            overview = favouriteEntityStub.overview,
            posterPath = favouriteEntityStub.posterPath,
            voteAverage = favouriteEntityStub.voteAverage,
            releaseDate = favouriteEntityStub.releaseDate
        )

        runTest {
            val result = repository.getFavouriteMovie(123)

            assertEquals(favouriteEntityStub, result)
        }
    }

    @Test
    fun getFavouriteMovie_returnNull() {
        runTest {
            val result = repository.getFavouriteMovie(1)

            assertEquals(null, result)
        }
    }

    @Test
    fun insertFavouriteMovie_movieInserted() {
        val queries = MovieDatabase(driver).favouriteEntitiyQueries

        runTest {

            val favouriteList = queries.getFavouriteMovies().asFlow().mapToList()

            val beforeCount = favouriteList.first().count()

            repository.insertFavouriteMovie(
                movieLocalStub
            )

            val afterCount = favouriteList.first().count()

            assertEquals(afterCount, beforeCount + 1)
        }
    }

    @Test
    fun deleteFavouriteMovie_movieDeleted() {
        val queries = MovieDatabase(driver).favouriteEntitiyQueries

        runTest {

            val favouriteList = queries.getFavouriteMovies().asFlow().mapToList()

            repository.insertFavouriteMovie(
                movieLocalStub
            )

            val beforeCount = favouriteList.first().count()

            repository.deleteFavouriteMovie(
                1
            )

            val afterCount = favouriteList.first().count()

            assertEquals(afterCount, beforeCount - 1)
        }
    }
}