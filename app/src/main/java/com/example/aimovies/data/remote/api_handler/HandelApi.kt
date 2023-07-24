package com.example.aimovies.data.remote.api_handler

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse

/**
 * Created by A.Elkhami on 21/07/2023.
 */
suspend inline fun <reified T : Any> handleApi(
    execute: () -> HttpResponse
): Result<T> {
    return try {
        val response = execute()

        val body = response.body<T>()

        Result.Success(body)
    } catch (e: RedirectResponseException) {
        // 3xx - responses
        Result.Error(e.response.status.description)
    } catch (e: ClientRequestException) {
        // 4xx - responses
        Result.Error(e.response.status.description)
    } catch (e: ServerResponseException) {
        // 5xx - responses
        Result.Error(e.response.status.description)
    } catch (e: Exception) {
        val response = execute()
        Result.Error(response.status.description)
    }
}