package com.example.aimovies.data.remote.api_handler

import com.example.aimovies.data.remote.isOnline
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
        if (isOnline()) {
            val response = execute()
            Result.Success(response.body())
        } else {
            Result.Error("No internet connection")
        }
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