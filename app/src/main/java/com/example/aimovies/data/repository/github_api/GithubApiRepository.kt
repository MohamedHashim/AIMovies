package com.example.aimovies.data.repository.github_api

import com.example.aimovies.data.remote.api_handler.Result

/**
 * Created by A.Elkhami on 03/10/2023.
 */
interface GithubApiRepository {
    suspend fun getGoogleCredentials(): Result<String>
}