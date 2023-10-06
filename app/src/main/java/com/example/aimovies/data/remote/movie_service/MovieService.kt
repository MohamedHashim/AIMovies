package com.example.aimovies.data.remote.movie_service

import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.discover.DiscoverMovieResponse
import com.example.aimovies.data.remote.dto.movie_details.MovieDetailsResponse

/**
 * Created by A.Elkhami on 30/08/2023.
 */
interface MovieService {
    suspend fun getDiscoverMovies(page: Int): Result<DiscoverMovieResponse>
    suspend fun getMovieById(movieId: Long): Result<MovieDetailsResponse>
}