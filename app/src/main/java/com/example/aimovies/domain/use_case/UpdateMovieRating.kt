package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.rating.MovieRatingRepository

/**
 * Created by A.Elkhami on 01/08/2023.
 */
class UpdateMovieRating(private val repository: MovieRatingRepository) {
    suspend operator fun invoke(movieId: Long, rating: Float) {
        repository.updateMovieRating(movieId, rating)
    }
}