package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.FavouriteMovieRepository
import com.example.aimovies.domain.mapper.toMovieLocalDto
import com.example.aimovies.domain.model.MovieModel

/**
 * Created by A.Elkhami on 25/07/2023.
 */
class InsertFavouriteMovie(private val repository: FavouriteMovieRepository) {
    suspend operator fun invoke(movie: MovieModel) {
        repository.insertFavouriteMovie(movie.toMovieLocalDto())
    }
}