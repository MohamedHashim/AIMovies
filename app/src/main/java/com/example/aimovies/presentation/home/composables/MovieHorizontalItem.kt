package com.example.aimovies.presentation.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.aimovies.R
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.presentation.ui.LocalSpacing
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme
import com.example.aimovies.presentation.ui.theme.MovieYellow

/**
 * Created by A.Elkhami on 18/07/2023.
 */
@Composable
fun MovieHorizontalItem(
    modifier: Modifier,
    movie: MovieModel,
    onClick: (MovieModel) -> Unit
) {
    val spacing = LocalSpacing.current
    val painter = rememberAsyncImagePainter(movie.posterPath)
    val state = painter.state

    Row(modifier = modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Max)
        .padding(
            spacing.spaceExtraSmall
        )
        .clip(RoundedCornerShape(spacing.curvedCornerSize))
        .clickable {
            onClick(movie)
        }
        .padding(spacing.spaceSmall)) {

        Box(contentAlignment = Alignment.Center) {
            if (state is AsyncImagePainter.State.Loading) {
                LoadingAnimation()
            }
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(spacing.curvedCornerSize))
                    .size(spacing.placeholderWidth, spacing.placeholderHeight),
                contentScale = ContentScale.Crop,
                model = movie.posterPath,
                onLoading = {

                },
                error = painterResource(id = R.drawable.movie_placeholder),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.spaceMedium),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = movie.title,
                modifier = Modifier.padding(top = spacing.spaceExtraSmall),
                maxLines = 3,
            )
            Text(
                text = movie.releaseDate,
                color = Color.Gray,
                modifier = Modifier.padding(top = spacing.spaceExtraSmall),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(modifier = Modifier.padding(top = spacing.spaceExtraLarge)) {
                Icon(
                    Icons.Default.Star,
                    modifier = Modifier.size(spacing.ratingIconSize),
                    tint = MovieYellow,
                    contentDescription = null
                )
                Text(
                    text = movie.voteAverage.toString(),
                    color = Color.Gray,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MovieHorizontalItemPreview() {
    AIMoviesTheme {
        MovieHorizontalItem(
            modifier = Modifier,
            movie = MovieModel(
                overview = "test",
                title = "The Demon Barber of Fleet Street",
                voteAverage = 8.5,
                posterPath = "https://assets-global.website-files.com/6009ec8cda7f305645c9d91b/6408f676b5811234c887ca62_top%20gun%20maverick-min.png",
                releaseDate = "01/03/2023"
            )
        ) {

        }
    }
}