package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.favourite.FavouriteMovieRepository
import com.example.aimovies.data.repository.favourite.favouriteEntityStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 26/07/2023.
 */
class GetFavouriteMovieTest {

    private lateinit var repository: FavouriteMovieRepository
    private lateinit var useCase: GetFavouriteMovie

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetFavouriteMovie(repository)
    }

    @Test
    fun getFavouriteMovie_returnFavouriteMovie() {
        coEvery { repository.getFavouriteMovie("title") } returns favouriteEntityStub

        runTest {
            val result = useCase("title")

            assertEquals(favouriteEntityStub, result)
        }
    }

    @Test
    fun getFavouriteMovie_returnNull() {
        coEvery { repository.getFavouriteMovie("title") } returns null

        runTest {
            val result = useCase("title")

            assertEquals(null, result)
        }
    }
}