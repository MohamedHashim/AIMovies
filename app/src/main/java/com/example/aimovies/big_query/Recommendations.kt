package com.example.aimovies.big_query

import android.content.res.Resources
import com.example.aimovies.R
import com.example.aimovies.big_query.mapper.convertTableResultToJson
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.bigquery.QueryJobConfiguration

/**
 * Created by A.Elkhami on 28/08/2023.
 */
class Recommendations {
    fun getRecommendations(resources: Resources, userId: String): String? {
        val credentials =
            GoogleCredentials.fromStream(resources.openRawResource(R.raw.ai_movies))
        val bigquery = BigQueryOptions.newBuilder().setCredentials(credentials).build().service

        val queryConfig = QueryJobConfiguration.newBuilder(Queries.recommendationQuery(userId))
            .build()

        val result = bigquery.query(queryConfig)

        return try {
             convertTableResultToJson(result)
        }catch (e: Exception){
            null
        }
    }
}