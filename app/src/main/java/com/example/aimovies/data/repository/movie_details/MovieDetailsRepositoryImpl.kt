package com.example.aimovies.data.repository.movie_details

import com.example.aimovies.big_query.dto.TopRecommendation
import com.example.aimovies.data.remote.movie_service.MovieService
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.dto.movie_details.MovieDetailsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow

/**
 * Created by A.Elkhami on 29/08/2023.
 */
class MovieDetailsRepositoryImpl(private val api: MovieService) : MovieDetailsRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getMovieById(recommendationsList: List<TopRecommendation>)
            : Flow<Result<MovieDetailsResponse>> {
        return recommendationsList.asFlow().flatMapMerge(concurrency = 10) { topRecommendation ->
            flow {
                emit(api.getMovieById(topRecommendation.tmdbId))
            }
        }
    }
}