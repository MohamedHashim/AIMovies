package com.example.aimovies.domain.use_case

import com.example.aimovies.big_query.dto.TopRecommendation
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.movie_details.MovieDetailsResponse
import com.example.aimovies.data.repository.movie_details.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 29/08/2023.
 */
class GetMovieDetailsById(private val repository: MovieDetailsRepository) {
    suspend operator fun invoke(recommendationsList: List<TopRecommendation>): Flow<Result<MovieDetailsResponse>> {
        return repository.getMovieById(recommendationsList)
    }
}