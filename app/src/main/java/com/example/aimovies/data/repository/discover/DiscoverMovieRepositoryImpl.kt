package com.example.aimovies.data.repository.discover

import com.example.aimovies.data.remote.MovieService
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.discover.DiscoverMovieResponse

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class DiscoverMovieRepositoryImpl(private val api: MovieService) : DiscoverMovieRepository {
    override suspend fun getDiscoverMovies(page: Int): Result<DiscoverMovieResponse> {
        return api.getDiscoverMovies(page)
    }
}