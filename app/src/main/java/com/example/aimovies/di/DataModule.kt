package com.example.aimovies.di

import android.util.Log
import com.example.aimovies.MovieDatabase
import com.example.aimovies.data.Constants.KtorLogger
import com.example.aimovies.data.local.FavouriteMovieDataSource
import com.example.aimovies.data.local.FavouriteMovieDataSourceImpl
import com.example.aimovies.data.remote.MovieService
import com.example.aimovies.data.repository.discover.DiscoverMovieRepository
import com.example.aimovies.data.repository.discover.DiscoverMovieRepositoryImpl
import com.example.aimovies.data.repository.favourite.FavouriteMovieRepository
import com.example.aimovies.data.repository.favourite.FavouriteMovieRepositoryImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * Created by A.Elkhami on 18/07/2023.
 */
val dataModule = module {
    single {
        provideHttpClient()
    }
    single {
        MovieService(get())
    }
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = MovieDatabase.Schema,
            context = get(),
            name = "movie.db"
        )
    }
    single<FavouriteMovieDataSource> {
        FavouriteMovieDataSourceImpl(MovieDatabase(get()), Dispatchers.IO)
    }
    single<FavouriteMovieRepository> {
        FavouriteMovieRepositoryImpl(get())
    }
    single<DiscoverMovieRepository> {
        DiscoverMovieRepositoryImpl(get())
    }
}

fun provideHttpClient(): HttpClient {
    return HttpClient(Android) {
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v(KtorLogger, message)
                }
            }
            level = LogLevel.BODY
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
}
