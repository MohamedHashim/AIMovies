package com.example.aimovies.domain.use_case

import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.movie_details.MovieDetailsResponse
import com.example.aimovies.data.repository.movie_details.MovieDetailsRepository
import com.example.aimovies.stub.movieDetailsResponseStub
import com.example.aimovies.stub.topRecommendationStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 30/08/2023.
 */
class GetMovieDetailsByIdTest {

    private lateinit var repository: MovieDetailsRepository

    private lateinit var useCase: GetMovieDetailsById

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetMovieDetailsById(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieDetailsById_success_returnMovieDetails() =
        runTest {
            val expected = flow {
                emit(
                    Result.Success(
                        movieDetailsResponseStub
                    )
                )
            }

            coEvery {
                repository.getMovieById(
                    listOf(
                        topRecommendationStub
                    )
                )
            } returns expected

            val moviesList = useCase(
                listOf(
                    topRecommendationStub
                )
            )

            advanceUntilIdle()

            Assert.assertEquals(expected, moviesList)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieDetailsById_error_returnNull() =
        runTest {
            val expected = flow { emit(Result.Error<MovieDetailsResponse>("error")) }

            coEvery {
                repository.getMovieById(
                    listOf(
                        topRecommendationStub
                    )
                )
            } returns expected

            val response = useCase(
                listOf(
                    topRecommendationStub
                )
            )

            advanceUntilIdle()

            Assert.assertEquals(expected, response)
        }
}