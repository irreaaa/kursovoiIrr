package com.example.myapplication.domain.usecase

import com.example.myapplication.data.remote.network.response.NetworkResponse
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.data.remote.network.response.SneakersResponse
import com.example.myapplication.data.repository.SneakersRepository

class CartUseCase (private  val sneakersRepository: SneakersRepository){
    suspend fun getCart(): NetworkResponseSneakers<List<SneakersResponse>>{
        return  sneakersRepository.getCart()
    }

    suspend fun addToCart(sneakerId: Int, inCart: Boolean): NetworkResponse<Unit>{
        return sneakersRepository.addToCart(sneakerId, inCart)
    }

    suspend fun removeFromCart(sneakerId: Int): NetworkResponse<Unit> {
        return sneakersRepository.removeFromCart(sneakerId)
    }
}