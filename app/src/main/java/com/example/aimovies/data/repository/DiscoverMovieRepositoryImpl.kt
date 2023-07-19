package com.example.aimovies.data.repository

import com.example.aimovies.BuildConfig
import com.example.aimovies.data.remote.MovieService
import com.example.aimovies.data.remote.dto.DiscoverMovieResponse

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class DiscoverMovieRepositoryImpl(private val api: MovieService) : DiscoverMovieRepository {
    override suspend fun getDiscoverMovies(page: Int): DiscoverMovieResponse? {
        return try {
            var response = api.getDiscoverMovies(page)
            response?.let {
                val results = it.results.map { movie ->
                    movie.copy(poster_path = BuildConfig.POSTER_BASE_URL + movie.poster_path)
                }
                response = it.copy(results = results)
            }
            response
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}