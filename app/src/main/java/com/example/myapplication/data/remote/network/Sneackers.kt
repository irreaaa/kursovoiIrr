package com.example.myapplication.data.remote.network

import com.example.myapplication.data.remote.network.response.SneakersResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Sneackers {
    @GET("/allSneakers")
    suspend fun getAllSneakers(): List<SneakersResponse>

    @GET("/sneakers/popular")
    suspend fun getPopularSneakers(): List<SneakersResponse>

    @GET("/sneakers/{category}")
    suspend fun getSneakersByCategory(@Path("category") category: String): List<SneakersResponse>

    @GET("/favorites")
    suspend fun getFavorites(): List<SneakersResponse>

    @POST("/favorites/{sneakerId}")
    suspend fun addToFavorites(@Path("sneakerId") sneakerId: Int): Response<ResponseBody>

    @DELETE("/favorites/{sneakerId}")
    suspend fun removeFromFavorites(@Path("sneakerId") sneakerId: Int): Response<ResponseBody>

    @GET("/cart")
    suspend fun getCart(): List<SneakersResponse>

    @POST("/cart/{sneakerId}")
    suspend fun addToCart(@Path("sneakerId") sneakerId: Int): Response<ResponseBody>

    @DELETE("/cart/{sneakerId}")
    suspend fun removeFromCart(@Path("sneakerId") sneakerId: Int): Response<ResponseBody>
}