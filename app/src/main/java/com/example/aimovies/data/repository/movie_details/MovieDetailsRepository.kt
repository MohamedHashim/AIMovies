package com.example.aimovies.data.repository.movie_details

import com.example.aimovies.big_query.dto.TopRecommendation
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.movie_details.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 29/08/2023.
 */
interface MovieDetailsRepository {
    suspend fun getMovieById(recommendationsList: List<TopRecommendation>): Flow<Result<MovieDetailsResponse>>
}