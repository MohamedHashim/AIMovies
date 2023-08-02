package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.favourite.FavouriteMovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 27/07/2023.
 */
class DeleteFavouriteMovieTest {

    private lateinit var repository: FavouriteMovieRepository
    private lateinit var useCase: DeleteFavouriteMovie

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteFavouriteMovie(repository)
    }

    @Test
    fun deleteFavouriteMovie_movieDeleted(){
        coEvery { repository.getFavouriteMovie(1) } returns null

        runTest {
            useCase(1)

            val result = repository.getFavouriteMovie(1)

            assertEquals(null, result)
        }
    }
}