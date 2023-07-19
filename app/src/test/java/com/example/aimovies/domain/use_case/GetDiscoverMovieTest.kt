package com.example.aimovies.domain.use_case

import com.example.aimovies.data.remote.dto.DiscoverMovieResponse
import com.example.aimovies.data.remote.dto.Movie
import com.example.aimovies.data.repository.DiscoverMovieRepositoryImpl
import com.example.aimovies.domain.mapper.toMovieModel
import com.example.aimovies.domain.model.MovieModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by A.Elkhami on 18/07/2023.
 */

@RunWith(MockitoJUnitRunner::class)
class GetDiscoverMovieTest {

    @Mock
    private lateinit var repository: DiscoverMovieRepositoryImpl

    private lateinit var useCase: GetDiscoverMovie

    @Before
    fun setUp() {
        useCase = GetDiscoverMovie(repository)
    }

    @Test
    fun getDiscoverMovie_success_returnMoviesList() {
        val responseStub = DiscoverMovieResponse(
            page = 1, results = listOf(
                Movie(
                    adult = false,
                    backdrop_path = "",
                    poster_path = "",
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

        runTest {
            `when`(
                repository.getDiscoverMovies(1)
            ).thenReturn(
                    responseStub
                )

            val expected = responseStub.results.map {
                it.toMovieModel()
            }

            val moviesList = useCase(1)

            assertEquals(expected, moviesList)
        }
    }

    @Test
    fun getDiscoverMovie_error_returnNull() {
        runTest {
            `when`(
                repository.getDiscoverMovies(1)
            ).thenReturn(
                    null
                )

            val expected = listOf<MovieModel>()

            val moviesList = useCase(1)

            assertEquals(expected, moviesList)
        }
    }
}