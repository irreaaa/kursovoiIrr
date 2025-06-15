package com.example.myapplication.data.remote.network.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val userName: String
)
