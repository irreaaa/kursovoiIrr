package com.example.myapplication.data.repository

interface OrderRepository {
    suspend fun sendConfirmationEmail(email: String, orderDetails: String)
}
