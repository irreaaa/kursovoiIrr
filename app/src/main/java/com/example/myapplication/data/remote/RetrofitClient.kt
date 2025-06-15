package com.example.myapplication.data.remote

import android.util.Log
import com.example.myapplication.data.local.LocalStorage
import com.example.myapplication.data.remote.network.Auth
import com.example.myapplication.data.remote.network.Sneackers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import java.util.concurrent.TimeUnit

class AuthInterceptor(
    private val dataStore: LocalStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStore.tokenFlow.first() }
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        Log.d("AUTH", "Token: $token")
        return chain.proceed(request)
    }
}

class RetrofitClient(
    private val authInterceptor: AuthInterceptor
) {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
//            .baseUrl("http://192.168.0.103:8080/")
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val auth: Auth by lazy {
        retrofit.create(Auth::class.java)
    }

    val sneakers: Sneackers by lazy {
        retrofit.create(Sneackers::class.java)
    }
}