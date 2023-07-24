package com.example.aimovies.domain.use_case

import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.DiscoverMovieResponse
import com.example.aimovies.data.repository.DiscoverMovieRepository

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class GetDiscoverMovie(private val repository: DiscoverMovieRepository) {
    suspend operator fun invoke(page: Int): Result<DiscoverMovieResponse> {
        return repository.getDiscoverMovies(page)
    }
}