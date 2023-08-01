package com.example.aimovies.di

import com.example.aimovies.domain.use_case.DeleteFavouriteMovie
import com.example.aimovies.domain.use_case.GetAllMovieRatings
import com.example.aimovies.domain.use_case.GetDiscoverMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovies
import com.example.aimovies.domain.use_case.GetMovieRating
import com.example.aimovies.domain.use_case.InsertFavouriteMovie
import com.example.aimovies.domain.use_case.InsertMovieRating
import com.example.aimovies.domain.use_case.UpdateMovieRating
import com.example.aimovies.presentation.home.HomeViewModel
import com.example.aimovies.presentation.overveiw.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by A.Elkhami on 18/07/2023.
 */
val homeModule = module {
    single {
        GetDiscoverMovie(get())
    }
    single {
        GetFavouriteMovies(get())
    }
    single {
        GetFavouriteMovie(get())
    }
    single {
        InsertFavouriteMovie(get())
    }
    single {
        DeleteFavouriteMovie(get())
    }
    single {
        GetAllMovieRatings(get())
    }
    single {
        GetMovieRating(get())
    }
    single {
        InsertMovieRating(get())
    }
    single {
        UpdateMovieRating(get())
    }
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        OverviewViewModel(get(), get(), get(), get(), get(), get())
    }
}