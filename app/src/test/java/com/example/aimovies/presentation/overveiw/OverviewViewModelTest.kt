package com.example.aimovies.presentation.overveiw

import com.example.aimovies.MainDispatcherRule
import com.example.aimovies.data.repository.favourite.favouriteEntityStub
import com.example.aimovies.domain.use_case.DeleteFavouriteMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovie
import com.example.aimovies.domain.use_case.InsertFavouriteMovie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by A.Elkhami on 26/07/2023.
 */
class OverviewViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var insertFavouriteMovieUseCase: InsertFavouriteMovie
    private lateinit var getFavouriteMovieUseCase: GetFavouriteMovie
    private lateinit var deleteFavouriteMovieUseCase: DeleteFavouriteMovie
    private lateinit var viewModel: OverviewViewModel

    @Before
    fun setUp() {
        insertFavouriteMovieUseCase = mockk()
        getFavouriteMovieUseCase = mockk()
        deleteFavouriteMovieUseCase = mockk()
        viewModel = OverviewViewModel(
            insertFavouriteMovieUseCase,
            getFavouriteMovieUseCase,
            deleteFavouriteMovieUseCase
        )
    }

    @Test
    fun checkIfMovieIsFavourite_returnTrue() {
        coEvery { getFavouriteMovieUseCase("title") } returns favouriteEntityStub

        runTest {
            viewModel.checkIfMovieIsFavourite("title")
        }
        assertTrue(viewModel.uiState.isMovieFavourite)
    }

    @Test
    fun checkIfMovieIsFavourite_returnFalse() {
        coEvery { getFavouriteMovieUseCase("title") } returns null

        runTest {
            viewModel.checkIfMovieIsFavourite("title")
        }
        assertFalse(viewModel.uiState.isMovieFavourite)
    }
}