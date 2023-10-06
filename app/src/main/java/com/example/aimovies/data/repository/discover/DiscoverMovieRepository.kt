package com.example.aimovies.data.repository.discover

import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.discover.DiscoverMovieResponse

/**
 * Created by A.Elkhami on 18/07/2023.
 */
interface DiscoverMovieRepository {
    suspend fun getDiscoverMovies(page: Int): Result<DiscoverMovieResponse>
}