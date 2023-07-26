package com.example.aimovies.data.repository.favourite

import com.example.aimovies.data.local.FavouriteMovieDataSource
import example.moviedb.FavouriteEntitiy
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 26/07/2023.
 */
class FavouriteMovieRepositoryTest {

    private lateinit var dataSource: FavouriteMovieDataSource
    private lateinit var repository: FavouriteMovieRepositoryImpl

    @Before
    fun setUp() {
        dataSource = mockk()
        repository = FavouriteMovieRepositoryImpl(dataSource)
    }

    @Test
    fun getFavouriteMovies_returnFavouriteMoviesList() {
        val expected = flow<List<FavouriteEntitiy>> {
            listOf(
                favouriteEntityStub
            )
        }
        every { dataSource.getFavouriteMovies() } returns expected

        val result = repository.getFavouriteMovies()

        assertEquals(expected, result)
    }

    @Test
    fun getFavouriteMovie_returnFavouriteMovie() {
        coEvery { dataSource.getFavouriteMovie("title") } returns favouriteEntityStub

        runTest {
            val result = repository.getFavouriteMovie("title")

            assertEquals(favouriteEntityStub, result)
        }
    }

    @Test
    fun getFavouriteMovie_returnNull() {
        coEvery { dataSource.getFavouriteMovie("title") } returns null

        runTest {
            val result = repository.getFavouriteMovie("title")

            assertEquals(null, result)
        }
    }

    @Test
    fun insertFavouriteMovie_movieInserted() {
        coEvery {
            dataSource.insertFavouriteMovie(
                id = 1,
                title = "",
                overview = "",
                posterPath = "",
                releaseDate = "",
                voteAverage = ""
            )
        }
    }

    @Test
    fun deleteFavouriteMovie_movieDeleted() {
        coEvery { dataSource.deleteFavouriteMovie(1) }
    }
}

val favouriteEntityStub = FavouriteEntitiy(
    id = 1,
    title = "title",
    overview = "overview",
    posterPath = "",
    voteAverage = "9.0",
    releaseDate = "10/10/2023"
)