package com.example.aimovies.presentation.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.aimovies.R
import com.example.aimovies.domain.model.MovieModel
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme

/**
 * Created by A.Elkhami on 18/07/2023.
 */
@Composable
fun MovieItem(
    modifier: Modifier,
    movie: MovieModel,
    onClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(movie.posterPath)
    val state = painter.state

    Column(modifier = modifier
        .width(IntrinsicSize.Min)
        .padding(top = 16.dp, bottom = 16.dp, start = 4.dp, end = 4.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable {
            onClick()
        }
        .padding(8.dp)) {

        Box(contentAlignment = Alignment.Center) {
            if (state is AsyncImagePainter.State.Loading) {
                LoadingAnimation()
            }
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .size(130.dp, 180.dp),
                contentScale = ContentScale.Crop,
                model = movie.posterPath,
                onLoading = {

                },
                error = painterResource(id = R.drawable.movie_placeholder),
                contentDescription = ""
            )
        }
        Text(
            text = movie.title,
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = movie.title,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = movie.voteAverage.toString(),
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MovieItemPreview() {
    AIMoviesTheme {
        MovieItem(
            modifier = Modifier,
            movie = MovieModel(
                overview = "test",
                title = "The Tromorrow Warrrrrrr",
                voteCount = 10,
                voteAverage = 8.5,
                posterPath = "https://assets-global.website-files.com/6009ec8cda7f305645c9d91b/6408f676b5811234c887ca62_top%20gun%20maverick-min.png",
                releaseDate = "01/03/2023"
            )
        ) {

        }
    }
}