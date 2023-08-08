package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.rating.MovieRatingRepository
import com.example.aimovies.stub.movieRateEntityStub
import example.moviedb.MovieRateEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 02/08/2023.
 */
class UpdateMovieRatingTest {

    private lateinit var repository: MovieRatingRepository
    private lateinit var useCase: UpdateMovieRating

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = UpdateMovieRating(repository)
    }

    @Test
    fun updateMovieRating_movieRatingUpdated() {
        coEvery {repository.getMovieRating(123) } returns  movieRateEntityStub

        val expectedRate = 7.0
        runTest {
            useCase(123, 7.0f)

            coEvery {repository.getMovieRating(123) } returns  MovieRateEntity(
                id = 1,
                movieId = 123,
                rate = 7.0
            )

            val result = repository.getMovieRating(123)

            assertNotEquals(movieRateEntityStub.rate, result?.rate)
            assertEquals(expectedRate, result?.rate)
        }
    }
}