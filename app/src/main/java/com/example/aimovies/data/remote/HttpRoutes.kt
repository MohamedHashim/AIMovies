package com.example.aimovies.data.remote

import com.example.aimovies.BuildConfig

/**
 * Created by A.Elkhami on 18/07/2023.
 */
object HttpRoutes {
    const val DISCOVER_MOVIES = "${BuildConfig.BASE_URL}/3/discover/movie?language=en&sort_by=popularity.desc"
    const val MOVIE_DETAILS = "${BuildConfig.BASE_URL}/3/movie/"
}