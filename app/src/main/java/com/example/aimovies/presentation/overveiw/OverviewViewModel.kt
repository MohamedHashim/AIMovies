package com.example.aimovies.presentation.overveiw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.domain.use_case.DeleteFavouriteMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovie
import com.example.aimovies.domain.use_case.InsertFavouriteMovie
import kotlinx.coroutines.launch

/**
 * Created by A.Elkhami on 25/07/2023.
 */
class OverviewViewModel(
    private val insertFavouriteMovieUseCase: InsertFavouriteMovie,
    private val getFavouriteMovieUseCase: GetFavouriteMovie,
    private val deleteFavouriteMovieUseCase: DeleteFavouriteMovie
) :
    ViewModel() {

    var uiState by mutableStateOf(OverviewUIModel())
        private set

    fun checkIfMovieIsFavourite(title: String) {
        viewModelScope.launch {
            getFavouriteMovieUseCase(title)?.let {
                uiState = uiState.copy(isMovieFavourite = true, movieId = it.id)
            } ?: run {
                uiState = uiState.copy(isMovieFavourite = false)
            }
        }
    }

    fun insertFavouriteMovie(movie: MovieModel) {
        viewModelScope.launch {
            insertFavouriteMovieUseCase(movie)
            uiState = uiState.copy(isMovieFavourite = true)
        }
    }

    fun deleteFavouriteMovie(id: Long) {
        viewModelScope.launch {
            deleteFavouriteMovieUseCase(id)
            uiState = uiState.copy(isMovieFavourite = false)
        }
    }
}