package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.FavouriteMovieRepository

/**
 * Created by A.Elkhami on 25/07/2023.
 */
class DeleteFavouriteMovie(private val repository: FavouriteMovieRepository) {
    suspend operator fun invoke(id: Long) {
        repository.deleteFavouriteMovie(id)
    }
}