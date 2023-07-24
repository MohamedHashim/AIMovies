package com.example.aimovies.presentation.home

import com.example.aimovies.domain.model.MovieModel

/**
 * Created by A.Elkhami on 19/07/2023.
 */
data class HomeUiModel(
    val discoverMovieList: List<MovieModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)
