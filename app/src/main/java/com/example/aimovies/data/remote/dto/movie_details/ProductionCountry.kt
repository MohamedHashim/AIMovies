package com.example.aimovies.data.remote.dto.movie_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String?,
    val name: String?
)