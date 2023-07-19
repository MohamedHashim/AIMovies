package com.example.aimovies.presentation.home

import com.example.aimovies.MainDispatcherRule
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.domain.use_case.GetDiscoverMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by A.Elkhami on 18/07/2023.
 */
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var useCase: GetDiscoverMovie
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun getDiscoverMovie_success_returnMoviesList() {
        val expected = listOf(
            MovieModel(
                overview = "",
                posterPath = "",
                releaseDate = "",
                voteAverage = 0.0,
                voteCount = 1,
                title = ""
            )
        )
        runTest {
            `when`(useCase(1))
                .thenReturn(
                    expected
                )

            viewModel.getDiscoverMovie(1)
        }

        Assert.assertEquals(expected, viewModel.uiState.discoverMovieList)
    }

    @Test
    fun getDiscoverMovie_error_returnEmptyList() {
        val expected = emptyList<MovieModel>()

        runTest {
            `when`(useCase(1)).thenReturn(
                emptyList()
            )

            viewModel.getDiscoverMovie(1)
        }

        Assert.assertEquals(expected, viewModel.uiState.discoverMovieList)
    }
}