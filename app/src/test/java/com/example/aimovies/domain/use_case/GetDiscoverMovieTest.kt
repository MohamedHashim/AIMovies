package com.example.aimovies.domain.use_case

import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.discover.DiscoverMovieResponse
import com.example.aimovies.data.repository.discover.DiscoverMovieRepositoryImpl
import com.example.aimovies.stub.discoverMovieResponseStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 18/07/2023.
 */

class GetDiscoverMovieTest {

    private lateinit var repository: DiscoverMovieRepositoryImpl

    private lateinit var useCase: GetDiscoverMovie

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetDiscoverMovie(repository)
    }

    @Test
    fun getDiscoverMovie_success_returnMoviesList() =
        runTest {
            val expected = Result.Success(
                discoverMovieResponseStub
            )

            coEvery { repository.getDiscoverMovies(1) } returns expected

            val moviesList = useCase(1)

            assertEquals(expected, moviesList)
        }

    @Test
    fun getDiscoverMovie_error_returnNull() =
        runTest {
            val expected = Result.Error<DiscoverMovieResponse>("error")

            coEvery { repository.getDiscoverMovies(1) } returns expected

            val response = useCase(1)

            assertEquals(expected, response)
        }
}