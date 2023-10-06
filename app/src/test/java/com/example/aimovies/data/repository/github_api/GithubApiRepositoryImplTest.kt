package com.example.aimovies.data.repository.github_api

import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.data.remote.github_service.GithubService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

/**
 * Created by A.Elkhami on 03/10/2023.
 */
class GithubApiRepositoryImplTest {

    private lateinit var api: GithubService
    private lateinit var repositoryImpl: GithubApiRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        repositoryImpl = GithubApiRepositoryImpl(api)
    }

    @Test
    fun getGoogleCredentials_returnJsonString_success() = runTest {
        val expected = Result.Success("json data")
        coEvery { api.getGoogleCredentials() } returns expected
        val response = repositoryImpl.getGoogleCredentials()
        assertEquals(expected, response)
    }

    @Test
    fun getGoogleCredentials_returnNull_error() = runTest {
        val expected = Result.Error<String>("error")
        coEvery { api.getGoogleCredentials() } returns expected
        val result = repositoryImpl.getGoogleCredentials()
        assertEquals(expected, result)
    }
}