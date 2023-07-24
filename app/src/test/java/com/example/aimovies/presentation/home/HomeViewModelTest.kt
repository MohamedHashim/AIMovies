package com.example.aimovies.presentation.home

import com.example.aimovies.MainDispatcherRule
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.domain.use_case.GetDiscoverMovie
import com.example.aimovies.stub.discoverMovieModelStub
import com.example.aimovies.stub.discoverMovieResponseStub
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
        val expected = discoverMovieModelStub
        coEvery { useCase(1) } returns Result.Success(discoverMovieResponseStub)

        runTest {
            viewModel.getDiscoverMovie(1)
        }

        Assert.assertEquals(expected, viewModel.uiState.discoverMovieList)
    }

    @Test
    fun getDiscoverMovie_error_returnEmptyList() {
        val expected = "Error"

        coEvery { useCase(1) } returns Result.Error(expected)

        runTest {
            viewModel.getDiscoverMovie(1)
        }

        Assert.assertEquals(expected, viewModel.uiState.errorMessage)
    }
}