package com.example.aimovies.big_query.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRecommendation(
    val genre: String,
    @SerialName("movie_title")
    val movieTitle: String,
    @SerialName("predicted_rating")
    val predictedRating: Double,
    val tmdbId: Long
)