package com.example.aimovies.presentation

import androidx.lifecycle.ViewModel
import com.example.aimovies.domain.model.MovieModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by A.Elkhami on 30/08/2023.
 */
class HomeSharedViewModel: ViewModel() {
    private val _sharedState = MutableStateFlow(MovieModel())
    val sharedState = _sharedState.asStateFlow()

    fun updateState(movie: MovieModel) {
        _sharedState.value = movie
    }
}