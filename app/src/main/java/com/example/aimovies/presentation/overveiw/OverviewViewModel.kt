package com.example.aimovies.presentation.overveiw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.domain.use_case.InsertFavouriteMovie
import kotlinx.coroutines.launch

/**
 * Created by A.Elkhami on 25/07/2023.
 */
class OverviewViewModel(private val insertFavouriteMovieUseCase: InsertFavouriteMovie) :
    ViewModel() {

    fun insertFavouriteMovie(movie: MovieModel) {
        viewModelScope.launch {
            insertFavouriteMovieUseCase(movie)
        }
    }
}