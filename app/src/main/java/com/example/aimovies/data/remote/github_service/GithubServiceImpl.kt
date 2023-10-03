package com.example.aimovies.data.remote.github_service

import com.example.aimovies.data.remote.HttpRoutes
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.api_handler.handleApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

/**
 * Created by A.Elkhami on 03/10/2023.
 */
class GithubServiceImpl(private val client: HttpClient): GithubService {
    override suspend fun getGoogleCredentials(): Result<String> {
        return handleApi {
            client.get{
//                header("Authorization", "Bearer ${HttpRoutes.PAT}")
                url(HttpRoutes.GOOGLE_CRED)
            }
        }
    }
}