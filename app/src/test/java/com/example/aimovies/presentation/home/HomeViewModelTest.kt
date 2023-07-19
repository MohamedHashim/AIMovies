package com.example.aimovies.presentation.home

import com.example.aimovies.MainDispatcherRule
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.domain.use_case.GetDiscoverMovie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var useCase: GetDiscoverMovie
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        useCase = mockk()
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
        coEvery { useCase(1) } returns expected

        runTest {
            viewModel.getDiscoverMovie(1)
        }

        Assert.assertEquals(expected, viewModel.uiState.discoverMovieList)
    }

    @Test
    fun getDiscoverMovie_error_returnEmptyList() {
        val expected = emptyList<MovieModel>()

        coEvery { useCase(1) } returns emptyList()

        runTest {
            viewModel.getDiscoverMovie(1)
        }

        Assert.assertEquals(expected, viewModel.uiState.discoverMovieList)
    }
}