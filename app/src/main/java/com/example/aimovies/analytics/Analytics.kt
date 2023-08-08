package com.example.aimovies.analytics

/**
 * Created by A.Elkhami on 03/08/2023.
 */
interface Analytics {
    fun logMovieRatingEvent(movieId: Long, rating: Double)
}