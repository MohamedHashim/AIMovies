package com.example.aimovies.di

import com.example.aimovies.data.remote.MovieService
import com.example.aimovies.data.remote.MovieServiceImpl
import com.example.aimovies.data.repository.DiscoverMovieRepository
import com.example.aimovies.data.repository.DiscoverMovieRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import org.koin.dsl.module

/**
 * Created by A.Elkhami on 18/07/2023.
 */
val dataModule = module {
    single<MovieService> {
        MovieServiceImpl(
            client = HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.BODY
                }
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }
        )
    }
    single<DiscoverMovieRepository> {
        DiscoverMovieRepositoryImpl(get())
    }
}