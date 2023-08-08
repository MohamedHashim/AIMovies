package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.rating.MovieRatingRepository
import example.moviedb.MovieRateEntity

/**
 * Created by A.Elkhami on 01/08/2023.
 */
class GetMovieRating(private val repository: MovieRatingRepository) {
    suspend operator fun invoke(movieId: Long): MovieRateEntity? {
        return repository.getMovieRating(movieId)
    }
}