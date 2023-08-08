package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.rating.MovieRatingRepository
import com.example.aimovies.stub.movieRateEntityStub
import example.moviedb.MovieRateEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 02/08/2023.
 */
class GetAllMovieRatingsTest {

    private lateinit var repository: MovieRatingRepository
    private lateinit var useCase: GetAllMovieRatings

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetAllMovieRatings(repository)
    }

    @Test
    fun getAllMovieRatings_returnRatedMoviesList() {
        val expected = flow<List<MovieRateEntity>> {
            listOf(
                movieRateEntityStub
            )
        }
        every { repository.getAllMovieRatings() } returns expected

        val result = useCase()

        assertEquals(expected, result)
    }
}