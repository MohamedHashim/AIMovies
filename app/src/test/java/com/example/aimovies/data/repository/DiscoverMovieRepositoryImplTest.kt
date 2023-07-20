package com.example.aimovies.data.repository

import com.example.aimovies.BuildConfig
import com.example.aimovies.data.remote.MovieService
import com.example.aimovies.data.remote.dto.DiscoverMovieResponse
import com.example.aimovies.data.remote.dto.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 19/07/2023.
 */
class DiscoverMovieRepositoryImplTest {

    private lateinit var api: MovieService
    private lateinit var repository: DiscoverMovieRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        repository = DiscoverMovieRepositoryImpl(api)
    }

    @Test
    fun getDiscoverMovies_success_returnDiscoverMovieResponse_withCorrectPosterUrl() {
        coEvery { api.getDiscoverMovies(1) } returns responseStub
        runTest {
            val response = repository.getDiscoverMovies(1)
            val results = responseStub.results.map { movie ->
                movie.copy(poster_path = BuildConfig.POSTER_BASE_URL + movie.poster_path)
            }
            val expected = responseStub.copy(results = results)
            assertEquals(expected, response)
            assertEquals(expected.results[0].poster_path, response!!.results[0].poster_path)
        }
    }

    @Test
    fun getDiscoverMovies_success_returnNull() {
        coEvery { api.getDiscoverMovies(1) } throws Exception()
        runTest {
            val response = repository.getDiscoverMovies(1)
            assertEquals(null, response)
        }
    }
}

val responseStub = DiscoverMovieResponse(
    page = 1, results = listOf(
        Movie(
            adult = false,
            backdrop_path = "",
            genre_ids = listOf(1, 2),
            poster_path = "",
            title = "",
            overview = "",
            vote_count = 1,
            video = false,
            vote_average = 0.0,
            popularity = 0.0,
            original_title = "",
            original_language = "",
            id = 1,
            release_date = ""
        )
    ),
    total_results = 1,
    total_pages = 1
)