package com.example.aimovies.big_query.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRecommendationsResponseItem(
    @SerialName("top_recommendations")
    val topRecommendations: List<TopRecommendation>,
    @SerialName("user_id")
    val userId: Int
)