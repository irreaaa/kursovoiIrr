package com.example.myapplication.data.remote.network

import com.example.dto.request.OrderConfirmationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {
    @POST("/send-confirmation")
    suspend fun sendOrderConfirmation(@Body request: OrderConfirmationRequest)
}
