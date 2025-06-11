package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.SneakersRepository
import com.example.myapplication.data.remote.network.response.NetworkResponse
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.data.remote.network.response.SneakersResponse

class FavoriteUseCase(private val sneakersRepository: SneakersRepository) {

    suspend fun getFavorites(): NetworkResponseSneakers<List<SneakersResponse>> {
        return sneakersRepository.getFavorites()
    }

    suspend fun toggleFavorite(sneakerId: Int, isFavorite: Boolean): NetworkResponse<Unit> {
        return sneakersRepository.toggleFavorite(sneakerId, isFavorite)
    }
}
