package com.example.aimovies.presentation.home

import com.example.aimovies.domain.model.MovieModel

/**
 * Created by A.Elkhami on 19/07/2023.
 */
data class DiscoverMoviesUiModel(
    val discoverMovieList: List<MovieModel> = emptyList(),
    val favouriteMovieList: List<MovieModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)
