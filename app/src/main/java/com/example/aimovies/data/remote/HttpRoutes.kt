package com.example.aimovies.data.remote

/**
 * Created by A.Elkhami on 18/07/2023.
 */
object HttpRoutes {
    private const val BASE_URL = "https://api.themoviedb.org"
    const val DISCOVER_MOVIES = "$BASE_URL/3/discover/movie?language=en&sort_by=popularity.desc"
}