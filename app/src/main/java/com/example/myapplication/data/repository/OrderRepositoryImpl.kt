package com.example.myapplication.data.repository

import com.example.dto.request.OrderConfirmationRequest
import com.example.myapplication.data.remote.network.OrderApi

class OrderRepositoryImpl(
    private val api: OrderApi
) : OrderRepository {
    override suspend fun sendConfirmationEmail(email: String, orderDetails: String) {
        api.sendOrderConfirmation(OrderConfirmationRequest(email, orderDetails))
    }
}