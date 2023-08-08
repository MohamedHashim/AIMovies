package com.example.aimovies.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimovies.BuildConfig
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.domain.mapper.toMovieModel
import com.example.aimovies.domain.use_case.GetDiscoverMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovies
import com.example.aimovies.presentation.home.mapper.toMovieModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class HomeViewModel(
    private val getDiscoverMovieUseCase: GetDiscoverMovie,
    private val getFavouriteMoviesUseCase: GetFavouriteMovies
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiModel())
        private set

    fun getDiscoverMovie(page: Int) {
        viewModelScope.launch {
            when (val response = getDiscoverMovieUseCase(page)) {
                is Result.Error -> {
                    uiState = uiState.copy(isLoading = false, errorMessage = response.message)
                }

                is Result.Success -> {
                    var data = response.data

                    val results = data.results.map { movie ->
                        movie.copy(
                            poster_path = BuildConfig.POSTER_BASE_URL + movie.poster_path,
                            release_date = movie.release_date?.substringBefore("-") ?: ""
                        )
                    }
                    data = data.copy(results = results)
                    val movieList = data.results.map { it.toMovieModel() }
                    uiState = uiState.copy(
                        discoverMovieList = movieList,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
            }
        }
    }

    fun getFavouriteMovies() {
        viewModelScope.launch {
            getFavouriteMoviesUseCase().collectLatest {
                uiState = uiState.copy(
                    favouriteMovieList = it.map { favouriteEntity ->
                        favouriteEntity.toMovieModel()
                    }
                )
            }
        }
    }
}