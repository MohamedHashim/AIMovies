package com.example.aimovies.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

/**
 * Created by A.Elkhami on 03/08/2023.
 */
class FirebaseAnalyticsImpl : Analytics {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    override fun logMovieRatingEvent(
        movieId: Long,
        userId: Long,
        movieName: String,
        rating: Double
    ) {
        firebaseAnalytics.logEvent("select_movie") {
            param("movie_id", movieId)
            param("movie_name", movieName)
            param("user_id", userId)
            param("movie_rating", rating)
        }
    }
}