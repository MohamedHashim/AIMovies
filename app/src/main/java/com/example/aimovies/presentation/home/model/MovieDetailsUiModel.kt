package com.example.aimovies.presentation.home.model

import com.example.aimovies.domain.model.MovieModel

/**
 * Created by A.Elkhami on 30/08/2023.
 */
data class MovieDetailsUiModel(
    val recommendedMovieList: List<MovieModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)
