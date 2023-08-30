package com.example.aimovies.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimovies.BuildConfig
import com.example.aimovies.big_query.dto.TopRecommendation
import com.example.aimovies.data.remote.api_handler.Result
import com.example.aimovies.domain.mapper.toMovieModel
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.domain.use_case.GetDiscoverMovie
import com.example.aimovies.domain.use_case.GetFavouriteMovies
import com.example.aimovies.domain.use_case.GetMovieDetailsById
import com.example.aimovies.presentation.home.mapper.toMovieModel
import com.example.aimovies.presentation.home.model.DiscoverMoviesUiModel
import com.example.aimovies.presentation.home.model.MovieDetailsUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class HomeViewModel(
    private val getDiscoverMovieUseCase: GetDiscoverMovie,
    private val getMovieByIdUseCase: GetMovieDetailsById,
    private val getFavouriteMoviesUseCase: GetFavouriteMovies
) : ViewModel() {
    var discoverMoviesUiState by mutableStateOf(DiscoverMoviesUiModel())
        private set

    var movieDetailsUiState by mutableStateOf(MovieDetailsUiModel())
        private set

    var selectedTab = mutableStateOf("Favorites")

    private val recommendedMoviesList = mutableListOf<MovieModel>()

    fun getDiscoverMovie(page: Int) {
        viewModelScope.launch {
            when (val response = getDiscoverMovieUseCase(page)) {
                is Result.Error -> {
                    discoverMoviesUiState = discoverMoviesUiState.copy(
                        isLoading = false,
                        errorMessage = response.message
                    )
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

                    discoverMoviesUiState = discoverMoviesUiState.copy(
                        discoverMovieList = movieList,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
            }
        }
    }

    fun getRecommendedMoviesById(recommendationsList: List<TopRecommendation>) {

        recommendedMoviesList.clear()

        movieDetailsUiState = movieDetailsUiState.copy(
            recommendedMovieList = recommendedMoviesList,
            isLoading = true
        )

        viewModelScope.launch {
            /* *
             * toList() function is used to wait for all emitted results
             * to come in order to process them for the UI.
             */
            val response = getMovieByIdUseCase(recommendationsList).toList()

            response.forEach { result ->
                when (result) {
                    is Result.Error -> {
                        movieDetailsUiState = movieDetailsUiState.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }

                    is Result.Success -> {
                        var movie = result.data

                        movie = movie.copy(
                            posterPath = BuildConfig.POSTER_BASE_URL + movie.posterPath,
                            releaseDate = movie.releaseDate?.substringBefore("-") ?: ""
                        )

                        recommendedMoviesList.add(movie.toMovieModel())

                        movieDetailsUiState = movieDetailsUiState.copy(
                            recommendedMovieList = recommendedMoviesList,
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                }
            }
        }
    }

    fun getFavouriteMovies() {
        viewModelScope.launch {
            getFavouriteMoviesUseCase().collectLatest {
                discoverMoviesUiState = discoverMoviesUiState.copy(
                    favouriteMovieList = it.map { favouriteEntity ->
                        favouriteEntity.toMovieModel()
                    }
                )
            }
        }
    }
}