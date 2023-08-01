package com.example.aimovies.presentation.home

import com.example.aimovies.MainDispatcherRule
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.domain.use_case.GetDiscoverMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovies
import com.example.aimovies.presentation.home.mapper.toMovieModel
import com.example.aimovies.stub.discoverMovieModelStub
import com.example.aimovies.stub.discoverMovieResponseStub
import com.example.aimovies.stub.favouriteEntityStub
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
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

    private lateinit var getDiscoverMovieUseCase: GetDiscoverMovie
    private lateinit var getFavouriteMoviesUseCase: GetFavouriteMovies
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        getDiscoverMovieUseCase = mockk()
        getFavouriteMoviesUseCase = mockk()
        viewModel = HomeViewModel(getDiscoverMovieUseCase, getFavouriteMoviesUseCase)
    }

    @Test
    fun getDiscoverMovie_success_returnMoviesList() {
        val expected = discoverMovieModelStub
        coEvery { getDiscoverMovieUseCase(1) } returns Result.Success(discoverMovieResponseStub)

        runTest {
            viewModel.getDiscoverMovie(1)
        }

        Assert.assertEquals(expected, viewModel.uiState.discoverMovieList)
    }

    @Test
    fun getDiscoverMovie_error_returnEmptyList() {
        val expected = "Error"

        coEvery { getDiscoverMovieUseCase(1) } returns Result.Error(expected)

        runTest {
            viewModel.getDiscoverMovie(1)
        }

        Assert.assertEquals(expected, viewModel.uiState.errorMessage)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getFavouriteMovies_returnFavouriteMovies() = runTest {
        val expectedFlow = flow {
            emit(
                listOf(
                    favouriteEntityStub
                )
            )
        }
        every { getFavouriteMoviesUseCase() } returns expectedFlow

        viewModel.getFavouriteMovies()

        val favouriteList = expectedFlow.first()

        val expected = favouriteList.map { favouriteEntity ->
            favouriteEntity.toMovieModel()
        }

        //waits till all tasks are finished.
        advanceUntilIdle()

        val result = viewModel.uiState.favouriteMovieList

        Assert.assertEquals(expected, result)
    }
}