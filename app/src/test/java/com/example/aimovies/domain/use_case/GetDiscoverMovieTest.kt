package com.example.aimovies.domain.use_case

import com.example.aimovies.data.remote.dto.DiscoverMovieResponse
import com.example.aimovies.data.remote.dto.Movie
import com.example.aimovies.data.repository.DiscoverMovieRepositoryImpl
import com.example.aimovies.domain.mapper.toMovieModel
import com.example.aimovies.domain.model.MovieModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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
            coEvery { repository.getDiscoverMovies(1) } returns responseStub

            val expected = responseStub.results.map {movie ->
                movie.toMovieModel()
            }

            val moviesList = useCase(1)

            assertEquals(expected, moviesList)
        }

    @Test
    fun getDiscoverMovie_error_returnNull() =
        runTest {
            coEvery { repository.getDiscoverMovies(1) } returns null

            val expected = listOf<MovieModel>()

            val moviesList = useCase(1)

            assertEquals(expected, moviesList)
        }

    private val responseStub = DiscoverMovieResponse(
        page = 1, results = listOf(
            Movie(
                adult = false,
                backdrop_path = "",
                poster_path = "123",
                release_date = "",
                overview = "",
                id = 1,
                genre_ids = listOf(1, 2),
                original_language = "",
                original_title = "",
                title = "",
                popularity = 0.0,
                video = false,
                vote_average = 0.0,
                vote_count = 0
            )
        ), total_pages = 1, total_results = 2
    )
}