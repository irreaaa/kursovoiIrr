package com.example.dto.request

data class OrderConfirmationRequest(
    val email: String,
    val orderDetails: String
)