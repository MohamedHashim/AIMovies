package com.example.aimovies.di

import com.example.aimovies.domain.use_case.GetDiscoverMovie
import org.koin.dsl.module

/**
 * Created by A.Elkhami on 18/07/2023.
 */
val domainModule = module {
    single {
        GetDiscoverMovie(get())
    }
}