package com.example.myapplication.data.remote.network.request

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest (
    val userName: String,
    val email: String,
    val password: String
)