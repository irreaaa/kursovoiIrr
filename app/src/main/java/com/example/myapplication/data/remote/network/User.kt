package com.example.myapplication.data.remote.network

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userName: String,
    val email: String,
    val password: String,
)
