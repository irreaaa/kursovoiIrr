package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.network.Auth
import com.example.myapplication.data.remote.network.request.LoginRequest
import com.example.myapplication.data.remote.network.request.RegistrationRequest
import com.example.myapplication.data.remote.network.response.TokenResponse

class AuthRepository(private val api: Auth) {
    suspend fun signUp(registrationRequest: RegistrationRequest): TokenResponse {
        return api.registration(registrationRequest)
    }
    suspend fun signIn(loginRequest: LoginRequest): TokenResponse {
        return api.authorization(loginRequest)
    }
}