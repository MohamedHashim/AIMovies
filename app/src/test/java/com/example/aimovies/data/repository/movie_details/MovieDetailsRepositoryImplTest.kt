package com.example.aimovies.data.repository.movie_details

import com.example.aimovies.data.remote.movie_service.MovieService
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.movie_details.MovieDetailsResponse
import com.example.aimovies.stub.movieDetailsResponseStub
import com.example.aimovies.stub.topRecommendationStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 30/08/2023.
 */
class MovieDetailsRepositoryImplTest {

    private lateinit var api: MovieService

    private lateinit var repository: MovieDetailsRepositoryImpl

    @Before
    fun setUp() {
        api = mockk(relaxed = true)
        repository = MovieDetailsRepositoryImpl(api)
    }

    @Test
    fun getDiscoverMovies_success_returnDiscoverMovieResponse_withCorrectPosterUrl() = runTest {
        val expected = Result.Success(movieDetailsResponseStub)
        coEvery { api.getMovieById(1) } returns expected

        val response = repository.getMovieById(listOf(topRecommendationStub)).first()

        assertEquals(expected, response)
    }

    @Test
    fun getDiscoverMovies_success_returnNull() = runTest {
        val expected = Result.Error<MovieDetailsResponse>("error")

        coEvery { api.getMovieById(1) } returns expected

        val response = repository.getMovieById(listOf(topRecommendationStub)).first()

        assertEquals(expected, response)
    }
}