package com.example.aimovies.domain.use_case

import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.repository.github_api.GithubApiRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 03/10/2023.
 */
class GetRecommendationsTest {

    private lateinit var githubRepository: GithubApiRepository
    private lateinit var getRecommendationsUseCase: GetRecommendations

    @Before
    fun setUp() {
        githubRepository = mockk()
        getRecommendationsUseCase = GetRecommendations(githubRepository)
    }

    @Test
    fun getRecommendations_returnNull_error() = runTest {
        coEvery { githubRepository.getGoogleCredentials() } returns Result.Error("error")
        val response = getRecommendationsUseCase("123")
        assertEquals(null, response)
    }
}