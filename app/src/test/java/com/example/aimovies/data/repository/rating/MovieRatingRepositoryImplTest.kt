package com.example.aimovies.data.repository.rating

import com.example.aimovies.MovieDatabase
import com.example.aimovies.data.local.rating.MovieRatingDataSourceImpl
import com.example.aimovies.stub.movieRateEntityStub
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 02/08/2023.
 */
class MovieRatingRepositoryImplTest {

    private lateinit var driver: JdbcSqliteDriver
    private lateinit var dataSource: MovieRatingDataSourceImpl
    private lateinit var repository: MovieRatingRepositoryImpl
    private lateinit var db: MovieDatabase

    @Before
    fun setUp() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MovieDatabase.Schema.create(driver)
        db = MovieDatabase(driver)
        dataSource = MovieRatingDataSourceImpl(db, Dispatchers.Default)
        repository = MovieRatingRepositoryImpl(dataSource)
    }

    @Test
    fun getAllMovieRatings_returnRatingsList() {
        val queries = db.movieRateEntitiyQueries

        queries.insertMovieRating(1, 123, 8.0)

        runTest {
            val expected = flow {
                emit(
                    listOf(
                        movieRateEntityStub
                    )
                )
            }.first()

            val result = repository.getAllMovieRatings().first()

            assertEquals(expected, result)
        }
    }

    @Test
    fun getMovieRating_returnMovieRating() {
        val queries = db.movieRateEntitiyQueries

        queries.insertMovieRating(1, 123, 8.0)

        runTest {
            val result = repository.getMovieRating(123)

            assertEquals(movieRateEntityStub, result)
        }
    }

    @Test
    fun getMovieRating_returnNull() {
        runTest {
            val result = repository.getMovieRating(1)

            assertEquals(null, result)
        }
    }

    @Test
    fun insertMovieRating_movieRatingInserted() {
        val queries = db.movieRateEntitiyQueries

        runTest {
            val ratedMoviesList = queries.getAllMovieRatings().asFlow().mapToList()
            val beforeCount = ratedMoviesList.first().count()

            repository.insertMovieRating(123, 8.0f)

            val afterCount = ratedMoviesList.first().count()

            assertEquals(afterCount, beforeCount + 1)
        }
    }

    @Test
    fun updateMovieRating_movieRatingUpdated() {
        val queries = db.movieRateEntitiyQueries

        val expectedRating = 7.0

        runTest {
            repository.insertMovieRating(123, 8.0f)
            val ratedMovieBeforeUpdate = queries.getMovieRating(123).executeAsOneOrNull()
            repository.updateMovieRating(123, 7.0f)
            val ratedMovieAfterUpdate = queries.getMovieRating(123).executeAsOneOrNull()

            assertNotEquals(ratedMovieBeforeUpdate?.rate, ratedMovieAfterUpdate?.rate)
            assertEquals(expectedRating, ratedMovieAfterUpdate?.rate)
        }
    }
}