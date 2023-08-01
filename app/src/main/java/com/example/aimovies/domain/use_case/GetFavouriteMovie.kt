package com.example.aimovies.domain.use_case

import com.example.aimovies.data.repository.favourite.FavouriteMovieRepository
import example.moviedb.FavouriteEntitiy

/**
 * Created by A.Elkhami on 26/07/2023.
 */
class GetFavouriteMovie(private val repository: FavouriteMovieRepository) {
    suspend operator fun invoke(title: String): FavouriteEntitiy? {
        return repository.getFavouriteMovie(title)
    }
}