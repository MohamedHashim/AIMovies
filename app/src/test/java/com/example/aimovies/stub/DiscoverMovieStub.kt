package com.example.aimovies.stub

import com.example.aimovies.data.remote.dto.discover.DiscoverMovieResponse
import com.example.aimovies.data.remote.dto.discover.Movie
import com.example.aimovies.domain.model.MovieModel

/**
 * Created by A.Elkhami on 24/07/2023.
 */

val discoverMovieResponseStub = DiscoverMovieResponse(
    page = 1, results = listOf(
        Movie(
            adult = false,
            backdrop_path = "",
            poster_path = "123",
            release_date = "10/10/2023",
            overview = "overview",
            id = 123,
            genre_ids = listOf(1, 2),
            original_language = "",
            original_title = "",
            title = "title",
            popularity = 0.0,
            video = false,
            vote_average = 9.0,
            vote_count = 0
        )
    ), total_pages = 1, total_results = 2
)

val discoverMovieModelStub = listOf(
    MovieModel(
        title = "title",
        movieId = 123,
        overview = "overview",
        posterPath = "https://image.tmdb.org/t/p/original123",
        voteAverage = 9.0,
        releaseDate = "10/10/2023"
    )
)