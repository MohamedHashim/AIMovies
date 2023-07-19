package com.example.aimovies.data.remote

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
                header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZWVkZTk2ZDUxNTQ4NTA1ZTY3YTY4NjFhY2U3NjBmYyIsInN1YiI6IjY0YjY1MmUxMzc4MDYyMDBmZjNhNWM0NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.g3GD-e6kvUmXyPXG8EJ8lAYPBEDZQy4LQzrmn8rngBA")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }
}