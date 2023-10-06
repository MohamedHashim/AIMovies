package com.example.aimovies.data.remote.github_service

import com.example.aimovies.data.remote.api_handler.Result

/**
 * Created by A.Elkhami on 03/10/2023.
 */
interface GithubService {
    suspend fun getGoogleCredentials(): Result<String>
}