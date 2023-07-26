package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.favourite.FavouriteMovieRepository
import com.example.aimovies.data.repository.favourite.favouriteEntityStub
import example.moviedb.FavouriteEntitiy
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 26/07/2023.
 */
class GetFavouriteMoviesTest {

    private lateinit var repository: FavouriteMovieRepository
    private lateinit var useCase: GetFavouriteMovies

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetFavouriteMovies(repository)
    }

    @Test
    fun getFavouriteMovies_returnFavouriteMoviesList() {
        val expected = flow<List<FavouriteEntitiy>> {
            listOf(
                favouriteEntityStub
            )
        }
        every { repository.getFavouriteMovies() } returns expected

        val result = useCase()

        assertEquals(expected, result)
    }
}