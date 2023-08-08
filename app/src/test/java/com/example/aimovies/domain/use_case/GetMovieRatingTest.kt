package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.rating.MovieRatingRepository
import com.example.aimovies.stub.movieRateEntityStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 02/08/2023.
 */
class GetMovieRatingTest {

    private lateinit var repository: MovieRatingRepository
    private lateinit var useCase: GetMovieRating

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetMovieRating(repository)
    }

    @Test
    fun getMovieRating_returnRatedMovie() {
        coEvery { repository.getMovieRating(1) } returns movieRateEntityStub

        runTest {
            val result = useCase(1)

            assertEquals(movieRateEntityStub, result)
        }
    }

    @Test
    fun getMovieRating_returnNull() {
        coEvery { repository.getMovieRating(1) } returns null

        runTest {
            val result = useCase(1)

            assertEquals(null, result)
        }
    }
}