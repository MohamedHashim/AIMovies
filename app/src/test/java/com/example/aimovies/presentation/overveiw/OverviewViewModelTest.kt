package com.example.aimovies.presentation.overveiw

import com.example.aimovies.MainDispatcherRule
import com.example.aimovies.analytics.Analytics
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.domain.use_case.DeleteFavouriteMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovie
import com.example.aimovies.domain.use_case.GetMovieRating
import com.example.aimovies.domain.use_case.InsertFavouriteMovie
import com.example.aimovies.domain.use_case.InsertMovieRating
import com.example.aimovies.domain.use_case.UpdateMovieRating
import com.example.aimovies.stub.favouriteEntityStub
import com.example.aimovies.stub.movieRateEntityStub
import com.google.firebase.analytics.FirebaseAnalytics
import example.moviedb.MovieRateEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
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
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var analytics: Analytics
    private lateinit var viewModel: OverviewViewModel

    @Before
    fun setUp() {
        insertFavouriteMovieUseCase = mockk(relaxed = true)
        getFavouriteMovieUseCase = mockk()
        deleteFavouriteMovieUseCase = mockk(relaxed = true)
        getMovieRatingUseCase = mockk()
        insertMovieRatingUseCase = mockk(relaxed = true)
        updateMovieRatingUseCase = mockk(relaxed = true)
        analytics = mockk(relaxed = true)
        firebaseAnalytics = mockkClass(FirebaseAnalytics::class)

        viewModel = OverviewViewModel(
            insertFavouriteMovieUseCase,
            getFavouriteMovieUseCase,
            deleteFavouriteMovieUseCase,
            getMovieRatingUseCase,
            insertMovieRatingUseCase,
            updateMovieRatingUseCase,
            analytics
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieRating_returnMovieRating() {
        coEvery { getMovieRatingUseCase(123) } returns movieRateEntityStub

        runTest {
            viewModel.getMovieRating(123)

            //waits till all tasks are finished.
            advanceUntilIdle()

            val expected = movieRateEntityStub.rate.toFloat()
            val actual = viewModel.uiState.rating

            assertEquals(expected, actual)
        }

        assertTrue(viewModel.isRatingAvailable)
    }

    @Test
    fun getMovieRating_returnNull() {
        coEvery { getMovieRatingUseCase(123) } returns null

        viewModel.getMovieRating(123)

        assertEquals(0.0f, viewModel.uiState.rating)

        assertFalse(viewModel.isRatingAvailable)
    }

    @Test
    fun insertOrUpdateRating_movieInserted() {
        viewModel.isRatingAvailable = false
        viewModel.insertOrUpdateRating(movieId = 123, title = "flash", rating = 8.0f, userId = 1)
        assertTrue(viewModel.isRatingAvailable)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertOrUpdateRating_movieUpdated() = runTest {
        viewModel.isRatingAvailable = true

        val expectedRaring = 9.0f

        coEvery { getMovieRatingUseCase(123) } returns movieRateEntityStub
        viewModel.getMovieRating(123)

        //waits till all tasks are finished.
        advanceUntilIdle()
        val beforeRating = viewModel.uiState.rating

        viewModel.insertOrUpdateRating(movieId = 123, title = "flash", rating = 8.0f, userId = 1)


        coEvery { getMovieRatingUseCase(123) } returns MovieRateEntity(
            id = 1,
            movieId = 123,
            rate = 9.0
        )
        viewModel.getMovieRating(123)

        //waits till all tasks are finished.
        advanceUntilIdle()
        val afterRating = viewModel.uiState.rating

        assertNotEquals(beforeRating, afterRating)
        assertEquals(expectedRaring, afterRating)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertMovieRating_movieInserted() = runTest {
        viewModel.insertMovieRating(123, 8.0f)
        //waits till all tasks are finished.
        advanceUntilIdle()
        coVerify { insertMovieRatingUseCase(123, 8.0f) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateMovieRating_movieUpdated() = runTest {
        viewModel.updateMovieRating(123, 8.0f)
        advanceUntilIdle()
        coVerify { updateMovieRatingUseCase(123, 8.0f) }
    }
}