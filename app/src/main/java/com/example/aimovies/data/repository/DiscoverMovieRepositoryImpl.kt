package com.example.aimovies.data.repository

import com.example.aimovies.data.remote.MovieService
import com.example.aimovies.data.remote.dto.DiscoverMovieResponse
import java.lang.Exception

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class DiscoverMovieRepositoryImpl(private val api: MovieService) : DiscoverMovieRepository {
    override suspend fun getDiscoverMovies(page: Int): DiscoverMovieResponse? {
        return try {
            api.getDiscoverMovies(page)
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}