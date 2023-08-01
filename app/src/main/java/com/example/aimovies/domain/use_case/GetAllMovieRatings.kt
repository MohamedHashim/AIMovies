package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.rating.MovieRatingRepository
import example.moviedb.MovieRateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by A.Elkhami on 01/08/2023.
 */
class GetAllMovieRatings(private val repository: MovieRatingRepository) {
    operator fun invoke(): Flow<List<MovieRateEntity>> {
        return repository.getAllMovieRatings()
    }
}