package com.example.aimovies.data.repository.discover

import com.example.aimovies.data.remote.MovieServiceImpl
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.discover.DiscoverMovieResponse
import com.example.aimovies.stub.discoverMovieResponseStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 19/07/2023.
 */
class DiscoverMovieRepositoryImplTest {

    private lateinit var api: MovieServiceImpl
    private lateinit var repository: DiscoverMovieRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        repository = DiscoverMovieRepositoryImpl(api)
    }

    @Test
    fun getDiscoverMovies_success_returnDiscoverMovieResponse_withCorrectPosterUrl() {
        val expected = Result.Success(discoverMovieResponseStub)
        coEvery { api.getDiscoverMovies(1) } returns expected
        runTest {
            val response = repository.getDiscoverMovies(1)

            assertEquals(expected, response)
        }
    }

    @Test
    fun getDiscoverMovies_success_returnNull() {
        val expected = Result.Error<DiscoverMovieResponse>("error")

        coEvery { api.getDiscoverMovies(1) } returns expected

        runTest {
            val response = repository.getDiscoverMovies(1)

            assertEquals(expected, response)
        }
    }
}