package com.example.aimovies.data.repository.github_api

import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.github_service.GithubService

/**
 * Created by A.Elkhami on 03/10/2023.
 */
class GithubApiRepositoryImpl(private val api: GithubService): GithubApiRepository {
    override suspend fun getGoogleCredentials(): Result<String> {
        return api.getGoogleCredentials()
    }
}