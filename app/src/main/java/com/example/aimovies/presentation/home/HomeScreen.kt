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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.presentation.home.composables.EmptyListView
import com.example.aimovies.presentation.home.composables.ErrorView
import com.example.aimovies.presentation.home.composables.MovieHorizontalItem
import com.example.aimovies.presentation.home.composables.MovieItem
import com.example.aimovies.presentation.home.composables.ToggleButton
import com.example.aimovies.presentation.home.composables.rememberForeverLazyListState
import com.example.aimovies.presentation.ui.LocalSpacing
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Created by A.Elkhami on 18/07/2023.
 */
@Composable
fun HomeScreen(
    onNavigateToOverview: (Long, String, String, String, String, String) -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    LaunchedEffect(key1 = true) {
        viewModel.getDiscoverMovie(1)
        viewModel.getFavouriteMovies()
    }
    HomeScreenUi(
        viewModel.uiState,
        onNavigateToOverview = {
            onNavigateToOverview(
                it.movieId,
                it.title,
                it.overview,
                it.releaseDate,
                URLEncoder.encode(it.posterPath, StandardCharsets.UTF_8.toString()),
                it.voteAverage.toString()
            )
        },
        onRefresh = { viewModel.getDiscoverMovie(1) })
}

@Composable
fun HomeScreenUi(
    uiState: HomeUiModel,
    onRefresh: () -> Unit,
    onNavigateToOverview: (MovieModel) -> Unit
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
            ErrorView(uiState.errorMessage) {
                onRefresh()
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(spacing.spaceMedium))
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    itemsIndexed(uiState.discoverMovieList) { index, movie ->
                        MovieItem(
                            modifier = Modifier.padding(
                                start = if (index == 0) spacing.spaceMedium else spacing.spaceExtraSmall,
                                end = if (index == uiState.discoverMovieList.size - 1) spacing.spaceMedium else spacing.spaceExtraSmall,
                                bottom = spacing.spaceMedium,
                                top = spacing.spaceSmall
                            ), movie = movie
                        ) {
                            onNavigateToOverview(it)
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
                if (uiState.favouriteMovieList.isEmpty()) {
                    EmptyListView(Icons.Default.Favorite, "No favourite movies yet.")
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = spacing.spaceExtraSmall),
                    state = rememberForeverLazyListState(key = "Overview")
                ) {
                    itemsIndexed(uiState.favouriteMovieList) { index, movie ->
                        MovieHorizontalItem(
                            modifier = Modifier.padding(
                                top = if (index == 0) spacing.spaceSmall else spacing.spaceExtraSmall,
                                bottom = if (index == uiState.discoverMovieList.size - 1) spacing.spaceSmall else spacing.spaceExtraSmall,
                                start = spacing.spaceMedium,
                                end = spacing.spaceMedium
                            ), movie = movie
                        ) {
                            onNavigateToOverview(it)
                        }
                    }
                }
            } else {
                EmptyListView(Icons.Default.ThumbUp, "No recommendations available yet")

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
        HomeScreenUi(HomeUiModel(HomeUiModel().discoverMovieList), {}, {})
    }
}