package com.example.aimovies.big_query.mapper

import com.google.cloud.bigquery.TableResult
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject

/**
 * Created by A.Elkhami on 28/08/2023.
 */

fun convertTableResultToJson(tableResult: TableResult): String {
    val gson = Gson()
    val jsonArray = JsonArray()

    for (row in tableResult.iterateAll()) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", row["user_id"].longValue)

        val recommendationsArray = JsonArray()
        for (recommendation in row["top_recommendations"].recordValue) {
            val recommendationObject = JsonObject()
            recommendationObject.addProperty(
                "tmdbId",
                recommendation.recordValue[0].longValue,
            )
            recommendationObject.addProperty(
                "movie_title",
                recommendation.recordValue[1].stringValue,
            )
            recommendationObject.addProperty(
                "genre",
                recommendation.recordValue[2].stringValue)
            recommendationObject.addProperty(
                "predicted_rating",
                recommendation.recordValue[3].doubleValue,
            )
            recommendationsArray.add(recommendationObject)
        }
        jsonObject.add("top_recommendations", recommendationsArray)

        jsonArray.add(jsonObject)
    }

    return gson.toJson(jsonArray)
}