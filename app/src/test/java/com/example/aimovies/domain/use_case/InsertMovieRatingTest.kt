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
class InsertMovieRatingTest {

    private lateinit var repository: MovieRatingRepository
    private lateinit var useCase: InsertMovieRating

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = InsertMovieRating(repository)
    }

    @Test
    fun insertMovieRating_movieRatingInserted(){
        coEvery { repository.getMovieRating(1) } returns movieRateEntityStub

        runTest {
            useCase(123, 8.0f)

            val result = repository.getMovieRating(1)

            assertEquals(movieRateEntityStub, result)
        }
    }
}