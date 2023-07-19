package com.example.aimovies.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.presentation.home.composables.MovieItem
import com.example.aimovies.presentation.ui.LocalSpacing
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme
import org.koin.androidx.compose.koinViewModel

/**
 * Created by A.Elkhami on 18/07/2023.
 */

val movies = listOf(
    MovieModel(
        overview = "test",
        title = "The Tomorrow War",
        voteCount = 10,
        voteAverage = 8.5,
        posterPath = "https://assets-global.website-files.com/6009ec8cda7f305645c9d91b/6408f676b5811234c887ca62_top%20gun%-min.png",
        releaseDate = "01/03/2023"
    ),
    MovieModel(
        overview = "test",
        title = "The Tromorrow War",
        voteCount = 10,
        voteAverage = 8.5,
        posterPath = "https://assets-global.website-files.com/6009ec8cda7f305645c9d91b/6408f676b5811234c887ca62_top%20gun%20maverick-min.png",
        releaseDate = "01/03/2023"
    ),
    MovieModel(
        overview = "test",
        title = "The Tromorrow War",
        voteCount = 10,
        voteAverage = 8.5,
        posterPath = "https://assets-global.website-files.com/6009ec8cda7f305645c9d91b/6408f676b5811234c887ca62_top%20gun%20maverick-min.png",
        releaseDate = "01/03/2023"
    )
)

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    viewModel.getDiscoverMovie(1)
    HomeScreenUi(viewModel.uiState.discoverMovieList)
}

@Composable
fun HomeScreenUi(
    discoverMovieList: List<MovieModel>
) {
    val spacing = LocalSpacing.current

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Discover Movies",
            fontWeight = FontWeight.Bold,
            fontSize = spacing.fontTitle,
            modifier = Modifier.padding(spacing.spaceMedium, spacing.space25)
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(discoverMovieList) { movie ->
                MovieItem(modifier = Modifier, movie = movie) {

                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    AIMoviesTheme {
        HomeScreenUi(movies)
    }
}