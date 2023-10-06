package com.example.aimovies.data.remote.dto.movie_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
    val id: Int?,
    @SerialName("logo_path")
    val logoPath: String?,
    val name: String?,
    @SerialName("origin_country")
    val originCountry: String?
)