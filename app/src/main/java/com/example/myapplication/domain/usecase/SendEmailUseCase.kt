package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.OrderRepository

class SendEmailUseCase(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(email: String, orderDetails: String) {
        orderRepository.sendConfirmationEmail(email, orderDetails)
    }
}
