package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.DiscoverMovieRepository
import com.example.aimovies.domain.mapper.toMovieModel
import com.example.aimovies.domain.model.MovieModel

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class GetDiscoverMovie(private val repository: DiscoverMovieRepository) {
    suspend operator fun invoke(page: Int): List<MovieModel> {
        val response = repository.getDiscoverMovies(page)

        response?.let {
            return it.results.map { movie ->
                movie.toMovieModel()
            }
        }.run {
            return emptyList()
        }
    }
}