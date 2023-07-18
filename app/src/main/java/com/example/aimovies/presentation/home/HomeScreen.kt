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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.presentation.home.composables.MovieItem
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme

/**
 * Created by A.Elkhami on 18/07/2023.
 */

val movies = listOf(
    MovieModel(
        overview = "test",
        title = "The Tromorrow Warrrrrrr",
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
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Discover Movies",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.padding(16.dp, 25.dp)
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(movies) { movie ->
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
        HomeScreen()
    }
}