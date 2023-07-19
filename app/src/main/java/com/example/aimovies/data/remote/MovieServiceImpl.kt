package com.example.aimovies.data.remote

import com.example.aimovies.BuildConfig
import com.example.aimovies.data.remote.dto.DiscoverMovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import java.lang.Exception

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class MovieServiceImpl(private val client: HttpClient) : MovieService {
    override suspend fun getDiscoverMovies(page: Int): DiscoverMovieResponse? {
        return try {
            client.get {
                url(HttpRoutes.DISCOVER_MOVIES)
                header("Authorization", BuildConfig.AUTH_KEY)
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }
}