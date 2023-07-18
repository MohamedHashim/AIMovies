package com.example.aimovies.data.remote

import com.example.aimovies.data.remote.dto.DiscoverMovieResponse

/**
 * Created by A.Elkhami on 18/07/2023.
 */
interface MovieService {
    suspend fun getDiscoverMovies(page: Int): DiscoverMovieResponse?
}