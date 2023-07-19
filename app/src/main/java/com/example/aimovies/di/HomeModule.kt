package com.example.aimovies.di

import com.example.aimovies.domain.use_case.GetDiscoverMovie
import com.example.aimovies.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by A.Elkhami on 18/07/2023.
 */
val homeModule = module {
    single {
        GetDiscoverMovie(get())
    }
    viewModel {
        HomeViewModel(get())
    }
}