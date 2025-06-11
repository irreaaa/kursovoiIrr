package com.example.myapplication.data.remote.network

import com.example.myapplication.data.remote.network.request.LoginRequest
import com.example.myapplication.data.remote.network.request.RegistrationRequest
import com.example.myapplication.data.remote.network.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Auth {
    @POST("/registration")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): TokenResponse

    @POST("/login")
    suspend fun authorization(@Body loginRequest: LoginRequest): TokenResponse
}