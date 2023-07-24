package com.example.aimovies.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.presentation.home.composables.MovieHorizontalItem
import com.example.aimovies.presentation.home.composables.MovieItem
import com.example.aimovies.presentation.home.composables.ToggleButton
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
    ), MovieModel(
        overview = "test",
        title = "The Tomorrow War",
        voteCount = 10,
        voteAverage = 8.5,
        posterPath = "https://assets-global.website-files.com/6009ec8cda7f305645c9d91b/6408f676b5811234c887ca62_top%20gun%20maverick-min.png",
        releaseDate = "01/03/2023"
    ), MovieModel(
        overview = "test",
        title = "The Tomorrow War",
        voteCount = 10,
        voteAverage = 8.5,
        posterPath = "https://assets-global.website-files.com/6009ec8cda7f305645c9d91b/6408f676b5811234c887ca62_top%20gun%20maverick-min.png",
        releaseDate = "01/03/2023"
    )
)

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    LaunchedEffect(key1 = true) {
        viewModel.getDiscoverMovie(1)
    }
    HomeScreenUi(viewModel.uiState)
}

@Composable
fun HomeScreenUi(
    uiState: HomeUiModel,
) {
    val spacing = LocalSpacing.current

    var selectedTab by remember {
        mutableStateOf("Favorites")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Discover Movies",
            fontWeight = FontWeight.Bold,
            fontSize = spacing.fontTitle,
            modifier = Modifier.padding(
                start = spacing.spaceMedium + spacing.spaceSmall,
                end = spacing.spaceMedium,
                top = spacing.mainTitleVerticalPadding
            )
        )
        if (uiState.errorMessage != "") {
            Text(
                text = uiState.errorMessage,
                fontWeight = FontWeight.Bold,
                fontSize = spacing.fontTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        start = spacing.spaceMedium,
                        end = spacing.spaceMedium,
                        top = spacing.spaceLarge
                    )
                    .fillMaxWidth()
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    itemsIndexed(uiState.discoverMovieList) { index, movie ->
                        MovieItem(
                            modifier = Modifier.padding(
                                start = if (index == 0) spacing.spaceMedium else spacing.spaceExtraSmall,
                                end = if (index == uiState.discoverMovieList.size - 1) 16.dp else spacing.spaceExtraSmall,
                                bottom = spacing.spaceMedium,
                                top = spacing.spaceMedium
                            ), movie = movie
                        ) {

                        }
                    }
                }
            }
            ToggleButton(
                modifier = Modifier
                    .padding(horizontal = spacing.spaceMedium),
                buttons = listOf("Favorites", "Recommended"),
                width = spacing.toggleButtonWidth,
                currentSelection = selectedTab
            ) {
                selectedTab = it
            }
            if (selectedTab == "Favorites") {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = spacing.spaceExtraSmall)
                ) {
                    itemsIndexed(uiState.discoverMovieList) { index, movie ->
                        MovieHorizontalItem(
                            modifier = Modifier.padding(
                                top = if (index == 0) spacing.spaceMedium else spacing.spaceExtraSmall,
                                bottom = if (index == uiState.discoverMovieList.size - 1) spacing.spaceMedium else spacing.spaceExtraSmall,
                                start = spacing.spaceMedium,
                                end = spacing.spaceMedium
                            ), movie = movie
                        ) {

                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(spacing.spaceMedium)
                ) {
                    items(emptyList<MovieModel>()) { movie ->
                        MovieHorizontalItem(modifier = Modifier, movie = movie) {

                        }
                    }
                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    AIMoviesTheme {
        HomeScreenUi(HomeUiModel(movies))
    }
}