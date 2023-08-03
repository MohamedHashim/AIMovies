package com.example.aimovies.analytics

/**
 * Created by A.Elkhami on 03/08/2023.
 */
interface Analytics {
    fun logAddFavouriteMovieEvent(movieId: Long, rating: Double)
}