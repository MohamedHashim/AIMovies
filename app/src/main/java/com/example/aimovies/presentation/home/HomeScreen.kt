package com.example.aimovies.presentation.home

import android.util.Log
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.aimovies.big_query.Recommendations
import com.example.aimovies.big_query.dto.TopRecommendationsResponseItem
import com.example.aimovies.big_query.mapper.jsonToRecommendedMovie
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.presentation.home.composables.EmptyListView
import com.example.aimovies.presentation.home.composables.ErrorView
import com.example.aimovies.presentation.home.composables.MovieHorizontalItem
import com.example.aimovies.presentation.home.composables.MovieItem
import com.example.aimovies.presentation.home.composables.ToggleButton
import com.example.aimovies.presentation.home.composables.rememberForeverLazyListState
import com.example.aimovies.presentation.home.model.DiscoverMoviesUiModel
import com.example.aimovies.presentation.home.model.MovieDetailsUiModel
import com.example.aimovies.presentation.ui.LocalSpacing
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        viewModel.discoverMoviesUiState,
        viewModel.movieDetailsUiState,
        viewModel.selectedTab,
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
        onRefresh = { viewModel.getDiscoverMovie(1) },
        onRecommendedResponseReceived = { recommendedResponse ->
            val response = recommendedResponse[0]
            viewModel.getRecommendedMoviesById(response.topRecommendations)
        })
}

@Composable
fun HomeScreenUi(
    discoverMoviesUiState: DiscoverMoviesUiModel,
    movieDetailsUiState: MovieDetailsUiModel,
    selectedTab: MutableState<String>,
    onRefresh: () -> Unit,
    onNavigateToOverview: (MovieModel) -> Unit,
    onRecommendedResponseReceived: (List<TopRecommendationsResponseItem>) -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

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
        if (discoverMoviesUiState.errorMessage != "") {
            ErrorView(discoverMoviesUiState.errorMessage) {
                onRefresh()
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                if (discoverMoviesUiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(spacing.spaceMedium))
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    itemsIndexed(discoverMoviesUiState.discoverMovieList) { index, movie ->
                        MovieItem(
                            modifier = Modifier.padding(
                                start = if (index == 0) spacing.spaceMedium else spacing.spaceExtraSmall,
                                end = if (index == discoverMoviesUiState.discoverMovieList.size - 1) spacing.spaceMedium else spacing.spaceExtraSmall,
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
                currentSelection = selectedTab.value
            ) {
                selectedTab.value = it
            }
            if (selectedTab.value == "Favorites") {
                if (discoverMoviesUiState.favouriteMovieList.isEmpty()) {
                    EmptyListView(Icons.Default.Favorite, "No favourite movies yet.")
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = spacing.spaceExtraSmall),
                    state = rememberForeverLazyListState(key = "Overview")
                ) {
                    itemsIndexed(discoverMoviesUiState.favouriteMovieList) { index, movie ->
                        MovieHorizontalItem(
                            modifier = Modifier.padding(
                                top = if (index == 0) spacing.spaceSmall else spacing.spaceExtraSmall,
                                bottom = if (index == discoverMoviesUiState.discoverMovieList.size - 1) spacing.spaceSmall else spacing.spaceExtraSmall,
                                start = spacing.spaceMedium,
                                end = spacing.spaceMedium
                            ), movie = movie
                        ) {
                            onNavigateToOverview(it)
                        }
                    }
                }
            } else {
                LaunchedEffect(key1 = true) {
                    coroutineScope.launch(Dispatchers.IO) {
                        val recommendedMoviesBigQuery =
                            Recommendations().getRecommendations(context.resources, "5306")

                        recommendedMoviesBigQuery?.let { recommendationResponse ->
                            val recommendedList = jsonToRecommendedMovie(recommendationResponse)

                            onRecommendedResponseReceived(recommendedList)

                            Log.i("taiga", recommendationResponse)
                        }
                    }
                }

                if (movieDetailsUiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(spacing.spaceMedium))
                }else {
                    if (movieDetailsUiState.recommendedMovieList.isEmpty()) {
                        EmptyListView(Icons.Default.ThumbUp, "No recommendations available yet")
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(spacing.spaceMedium)
                        ) {
                            items(movieDetailsUiState.recommendedMovieList) { movie ->
                                MovieHorizontalItem(modifier = Modifier, movie = movie) {
                                    onNavigateToOverview(it)
                                }
                            }
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
        val selectedTab = remember {
            mutableStateOf("")
        }
        HomeScreenUi(
            DiscoverMoviesUiModel(DiscoverMoviesUiModel().discoverMovieList),
            MovieDetailsUiModel(),
            selectedTab,
            {},
            {},
            {})
    }
}