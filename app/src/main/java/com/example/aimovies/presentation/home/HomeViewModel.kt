package com.example.aimovies.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimovies.domain.use_case.GetDiscoverMovie
import kotlinx.coroutines.launch

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class HomeViewModel(private val getDiscoverMovieUseCase: GetDiscoverMovie) : ViewModel() {
    var uiState by mutableStateOf(HomeUiModel())
        private set

    fun getDiscoverMovie(page: Int) {
        viewModelScope.launch {
            val movies = getDiscoverMovieUseCase(page)
            uiState = uiState.copy(discoverMovieList = movies, isLoading = false)
        }
    }
}