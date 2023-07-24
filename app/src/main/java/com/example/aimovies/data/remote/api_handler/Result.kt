package com.example.aimovies.data.remote.api_handler

/**
 * Created by A.Elkhami on 20/07/2023.
 */
sealed interface Result<T : Any> {
    class Success<T : Any>(val data: T) :
        Result<T>
    class Error<T : Any>(val message: String) :
        Result<T>
}
