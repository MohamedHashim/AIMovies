package com.example.aimovies.presentation.overveiw

import com.example.aimovies.MainDispatcherRule
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.domain.use_case.DeleteFavouriteMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovie
import com.example.aimovies.domain.use_case.GetMovieRating
import com.example.aimovies.domain.use_case.InsertFavouriteMovie
import com.example.aimovies.domain.use_case.InsertMovieRating
import com.example.aimovies.domain.use_case.UpdateMovieRating
import com.example.aimovies.stub.favouriteEntityStub
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
    private lateinit var getMovieRatingUseCase: GetMovieRating
    private lateinit var insertMovieRatingUseCase: InsertMovieRating
    private lateinit var updateMovieRatingUseCase: UpdateMovieRating
    private lateinit var viewModel: OverviewViewModel

    @Before
    fun setUp() {
        insertFavouriteMovieUseCase = mockk(relaxed = true)
        getFavouriteMovieUseCase = mockk()
        deleteFavouriteMovieUseCase = mockk(relaxed = true)
        getMovieRatingUseCase = mockk()
        insertMovieRatingUseCase = mockk(relaxed = true)
        updateMovieRatingUseCase = mockk(relaxed = true)

        viewModel = OverviewViewModel(
            insertFavouriteMovieUseCase,
            getFavouriteMovieUseCase,
            deleteFavouriteMovieUseCase,
            getMovieRatingUseCase,
            insertMovieRatingUseCase,
            updateMovieRatingUseCase
        )
    }

    @Test
    fun checkIfMovieIsFavourite_returnTrue() {
        coEvery { getFavouriteMovieUseCase(1) } returns favouriteEntityStub

        runTest {
            viewModel.checkIfMovieIsFavourite(1)
        }
        assertTrue(viewModel.uiState.isMovieFavourite)
    }

    @Test
    fun checkIfMovieIsFavourite_returnFalse() {
        coEvery { getFavouriteMovieUseCase(1) } returns null

        runTest {
            viewModel.checkIfMovieIsFavourite(1)
        }
        assertFalse(viewModel.uiState.isMovieFavourite)
    }

    @Test
    fun insertFavouriteMovie_movieInserted() {
        viewModel.insertFavouriteMovie(
            MovieModel(
                movieId = 1,
                title = "title",
                overview = "overview",
                posterPath = "",
                voteAverage = 9.0,
                releaseDate = "10/10/2023"
            )
        )
        coEvery { getFavouriteMovieUseCase(1) } returns favouriteEntityStub
        runTest {
            viewModel.checkIfMovieIsFavourite(1)
        }
        assertTrue(viewModel.uiState.isMovieFavourite)
    }

    @Test
    fun deleteFavouriteMovie_movieDeleted() {
        viewModel.deleteFavouriteMovie(
            1
        )
        coEvery { getFavouriteMovieUseCase(1) } returns null

        runTest {
            viewModel.checkIfMovieIsFavourite(1)
        }

        assertFalse(viewModel.uiState.isMovieFavourite)
    }

    @Test
    fun getMovieRating_returnMovieRating(){

    }

    @Test
    fun insertOrUpdateRating_movieInserted(){

    }

    @Test
    fun insertOrUpdateRating_movieUpdated(){

    }

    @Test
    fun insertMovieRating_movieInserted(){

    }

    @Test
    fun updateMovieRating_movieUpdated(){

    }
}