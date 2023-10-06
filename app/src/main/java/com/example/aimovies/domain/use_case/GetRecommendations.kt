package com.example.aimovies.domain.use_case

import com.example.aimovies.big_query.Queries
import com.example.aimovies.big_query.mapper.convertTableResultToJson
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.repository.github_api.GithubApiRepository
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.bigquery.QueryJobConfiguration

/**
 * Created by A.Elkhami on 28/08/2023.
 */
class GetRecommendations(private val githubRepository: GithubApiRepository) {
    suspend operator fun invoke(userId: String): String? {
        return try {
            when (val response = githubRepository.getGoogleCredentials()) {
                is Result.Error -> {
                    null
                }

                is Result.Success -> {
                    val credentials =
                        GoogleCredentials.fromStream(response.data.byteInputStream())
                    val bigquery =
                        BigQueryOptions.newBuilder().setCredentials(credentials).build().service

                    val queryConfig = QueryJobConfiguration.newBuilder(
                        Queries.recommendationQuery(
                            userId
                        )
                    )
                        .build()

                    val result = bigquery.query(queryConfig)

                    convertTableResultToJson(result)
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}