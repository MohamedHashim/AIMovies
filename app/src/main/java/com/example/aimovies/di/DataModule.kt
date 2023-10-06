package com.example.aimovies.di

import android.util.Log
import com.example.aimovies.MovieDatabase
import com.example.aimovies.data.Constants.KtorLogger
import com.example.aimovies.data.local.favourite.FavouriteMovieDataSource
import com.example.aimovies.data.local.favourite.FavouriteMovieDataSourceImpl
import com.example.aimovies.data.local.rating.MovieRatingDataSource
import com.example.aimovies.data.local.rating.MovieRatingDataSourceImpl
import com.example.aimovies.data.remote.github_service.GithubService
import com.example.aimovies.data.remote.github_service.GithubServiceImpl
import com.example.aimovies.data.remote.movie_service.MovieService
import com.example.aimovies.data.remote.movie_service.MovieServiceImpl
import com.example.aimovies.data.repository.discover.DiscoverMovieRepository
import com.example.aimovies.data.repository.discover.DiscoverMovieRepositoryImpl
import com.example.aimovies.data.repository.favourite.FavouriteMovieRepository
import com.example.aimovies.data.repository.favourite.FavouriteMovieRepositoryImpl
import com.example.aimovies.data.repository.github_api.GithubApiRepository
import com.example.aimovies.data.repository.github_api.GithubApiRepositoryImpl
import com.example.aimovies.data.repository.movie_details.MovieDetailsRepository
import com.example.aimovies.data.repository.movie_details.MovieDetailsRepositoryImpl
import com.example.aimovies.data.repository.rating.MovieRatingRepository
import com.example.aimovies.data.repository.rating.MovieRatingRepositoryImpl
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
    single<MovieService> {
        MovieServiceImpl(get())
    }
    single<GithubService> {
        GithubServiceImpl(get())
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
    single<MovieRatingDataSource> {
        MovieRatingDataSourceImpl(MovieDatabase(get()), Dispatchers.IO)
    }
    single<FavouriteMovieRepository> {
        FavouriteMovieRepositoryImpl(get())
    }
    single<MovieRatingRepository> {
        MovieRatingRepositoryImpl(get())
    }
    single<DiscoverMovieRepository> {
        DiscoverMovieRepositoryImpl(get())
    }
    single<MovieDetailsRepository> {
        MovieDetailsRepositoryImpl(get())
    }
    single<GithubApiRepository> {
        GithubApiRepositoryImpl(get())
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
