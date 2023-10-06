package com.example.aimovies.big_query.mapper

import com.example.aimovies.big_query.dto.TopRecommendationsResponseItem
import kotlinx.serialization.json.Json

/**
 * Created by A.Elkhami on 29/08/2023.
 */

fun jsonToRecommendedMovie(json: String): List<TopRecommendationsResponseItem> {
    return Json.decodeFromString(json)
}