package com.example.myapplication.data.remote.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SneakersResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    @SerialName("isPopular")
    val isPopular: Boolean,
    @SerialName("isFavorite")
    val isFavorite: Boolean = false,
    @SerialName("inCart")
    val inCart: Boolean = false,
)