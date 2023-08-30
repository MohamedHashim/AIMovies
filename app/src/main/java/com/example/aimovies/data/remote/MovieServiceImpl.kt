package com.example.aimovies.data.remote

import com.example.aimovies.BuildConfig
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.api_handler.handleApi
import com.example.aimovies.data.remote.dto.discover.DiscoverMovieResponse
import com.example.aimovies.data.remote.dto.movie_details.MovieDetailsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class MovieServiceImpl(private val client: HttpClient): MovieService {
    override suspend fun getDiscoverMovies(page: Int): Result<DiscoverMovieResponse> {
        return handleApi {
            client.get {
                header("Authorization", BuildConfig.AUTH_KEY)
                url(HttpRoutes.DISCOVER_MOVIES)
                parameter("page", page)
            }
        }
    }

    override suspend fun getMovieById(movieId: Long): Result<MovieDetailsResponse> {
        return (handleApi {
                client.get {
                    header("Authorization", BuildConfig.AUTH_KEY)
                    url(HttpRoutes.MOVIE_DETAILS + movieId)
                }
            })
        }
    }
