package com.example.aimovies.presentation.overveiw

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.aimovies.R
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.presentation.home.composables.LoadingAnimation
import com.example.aimovies.presentation.ui.LocalSpacing
import com.example.aimovies.presentation.ui.theme.MovieYellow
import com.example.aimovies.presentation.ui.theme.RateBackground
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import org.koin.androidx.compose.koinViewModel
import java.lang.Float.min

/**
 * Created by A.Elkhami on 25/07/2023.
 */
@Composable
fun OverviewScreen(
    movieId: Long,
    title: String,
    overview: String,
    releaseDate: String,
    posterPath: String,
    voteAverage: String,
    navController: NavHostController
) {
    val viewModel = koinViewModel<OverviewViewModel>()

    val movie = MovieModel(
        movieId = movieId,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage.toDouble()
    )

    viewModel.checkIfMovieIsFavourite(movieId)
    viewModel.getMovieRating(movieId)

    val uiState = viewModel.uiState

    OverviewScreenUi(
        movieId = movieId,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage,
        uiState = uiState,
        navController = navController,
        onAddFavouriteClick = {
            if (uiState.isMovieFavourite) {
                viewModel.deleteFavouriteMovie(movieId)
            } else {
                viewModel.insertFavouriteMovie(movie)
            }
        },
        onRatingSelected = {
            viewModel.insertOrUpdateRating(movieId = movieId, rating = it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreenUi(
    movieId: Long,
    title: String,
    overview: String,
    releaseDate: String,
    posterPath: String,
    voteAverage: String,
    uiState: OverviewUIModel,
    navController: NavHostController?,
    onAddFavouriteClick: (MovieModel) -> Unit,
    onRatingSelected: (Float) -> Unit
) {
    val spacing = LocalSpacing.current
    val painter = rememberAsyncImagePainter(posterPath)
    val state = painter.state
    val scrollState = rememberScrollState()
    var movieRating by remember {
        mutableStateOf(uiState.rating)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        floatingActionButton = {
            FloatingActionButton(
                shape = RoundedCornerShape(spacing.curvedCornerSize),
                onClick = {
                    onAddFavouriteClick(
                        MovieModel(
                            movieId = movieId,
                            title = title,
                            overview = overview,
                            releaseDate = releaseDate,
                            posterPath = posterPath,
                            voteAverage = voteAverage.toDouble()
                        )
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (uiState.isMovieFavourite) MovieYellow else Color.White,
                    modifier = Modifier.padding(spacing.spaceMedium)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                bottomStart = spacing.curvedCornerSize,
                                bottomEnd = spacing.curvedCornerSize
                            )
                        )
                        .verticalScroll(scrollState),
                ) {
                    Box(
                        modifier = Modifier.graphicsLayer {
                            alpha = min(1f, 1 - (scrollState.value / 600f))
                            translationY = -scrollState.value * 0.1f
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        if (state is AsyncImagePainter.State.Loading) {
                            LoadingAnimation()
                        }
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(spacing.overviewImageBackgroundSize)
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = spacing.spaceLarge,
                                        bottomEnd = spacing.spaceLarge
                                    )
                                )
                                .blur(spacing.spaceLarge, spacing.spaceLarge),
                            contentScale = ContentScale.FillWidth,
                            model = posterPath,
                            onLoading = {

                            },
                            error = painterResource(id = R.drawable.movie_placeholder),
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(spacing.overviewImageSize)
                                    .clip(
                                        RoundedCornerShape(spacing.spaceExtraLarge)
                                    ),
                                contentScale = ContentScale.Fit,
                                model = posterPath,
                                onLoading = {

                                },
                                error = painterResource(id = R.drawable.movie_placeholder),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            RatingBar(
                                value = movieRating,
                                style = RatingBarStyle.Fill(MovieYellow),
                                stepSize = StepSize.HALF,
                                onValueChange = {
                                    movieRating = it
                                },
                                onRatingChanged = {
                                    onRatingSelected(it)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(spacing.spaceMedium))
                                    .background(RateBackground)
                                    .padding(spacing.spaceMedium)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier.padding(
                                top = spacing.spaceSmall,
                                start = spacing.spaceMedium,
                                end = spacing.spaceMedium
                            ),
                            maxLines = 3,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(top = spacing.spaceSmall),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    modifier = Modifier.size(spacing.ratingIconSize),
                                    tint = MovieYellow,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                                Text(
                                    text = voteAverage,
                                    color = Color.Gray,
                                )
                            }
                            Spacer(modifier = Modifier.width(spacing.spaceMedium))
                            Text(
                                text = releaseDate,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = spacing.spaceSmall),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Text(
                            text = overview,
                            modifier = Modifier.padding(
                                top = spacing.spaceSmall,
                                bottom = spacing.spaceExtraLarge + spacing.spaceMedium,
                                start = spacing.spaceMedium,
                                end = spacing.spaceMedium
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Box(modifier = Modifier
                    .padding(start = spacing.spaceMedium, top = spacing.spaceMedium)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        navController?.popBackStack()
                    }
                    .padding(spacing.spaceSmall)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    OverviewScreenUi(
        movieId = 1,
        title = "The Demon Barber of Fleet Street",
        overview = "Former cinema superhero Riggan Thomson (Michael Keaton) is mounting an ambitious Broadway production that he hopes will breathe new life into his stagnant career. It's risky, but he hopes that his creative gamble will prove that he's a real artist and not just a washed-up movie star. As opening night approaches, a castmate is injured, forcing Riggan to hire an actor (Edward Norton) who is guaranteed to shake things up. Meanwhile, Riggan must deal with his girlfriend, daughter and ex-wife.",
        releaseDate = "2023",
        posterPath = "https://assets-global.website-files.com/6009ec8cda7f305645c9d91b/6408f676b5811234c887ca62_top%20gun%20maverick-min.png",
        voteAverage = "8.5",
        navController = null,
        uiState = OverviewUIModel(),
        onAddFavouriteClick = {},
        onRatingSelected = {}
    )
}