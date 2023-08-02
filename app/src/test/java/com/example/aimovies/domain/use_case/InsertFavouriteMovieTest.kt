package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.favourite.FavouriteMovieRepository
import com.example.aimovies.stub.favouriteEntityStub
import com.example.aimovies.stub.movieModelStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 27/07/2023.
 */
class InsertFavouriteMovieTest {

    private lateinit var repository: FavouriteMovieRepository
    private lateinit var useCase: InsertFavouriteMovie

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = InsertFavouriteMovie(repository)
    }

    @Test
    fun insertFavouriteMovie_movieInserted(){
        coEvery { repository.getFavouriteMovie(1) } returns favouriteEntityStub

        runTest {
            useCase(movieModelStub)

            val result = repository.getFavouriteMovie(1)

            assertEquals(favouriteEntityStub, result)
        }
    }
}