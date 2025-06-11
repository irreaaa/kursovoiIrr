package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.SneakersRepository
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.data.remote.network.response.SneakersResponse

class SneakersUseCase(private val sneakersRepository: SneakersRepository) {

    suspend fun getAllSneakers(): NetworkResponseSneakers<List<SneakersResponse>> {
        return sneakersRepository.getAllSneakers()
    }

    suspend fun getPopularSneakers(): NetworkResponseSneakers<List<SneakersResponse>> {
        return sneakersRepository.getPopularSneakers()
    }

    suspend fun getSneakersByCategory(category: String): NetworkResponseSneakers<List<SneakersResponse>> {
        return sneakersRepository.getSneakersByCategory(category)
    }
}