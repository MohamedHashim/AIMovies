package com.example.aimovies.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

/**
 * Created by A.Elkhami on 03/08/2023.
 */
class FirebaseAnalyticsImpl: Analytics {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    override fun logAddFavouriteMovieEvent(movieId: Long, rating: Double) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, movieId)
            param(FirebaseAnalytics.Param.ITEM_NAME, rating)
        }
    }
}