package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.network.Sneackers
import com.example.myapplication.data.remote.network.response.NetworkResponse
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.data.remote.network.response.SneakersResponse

class SneakersRepository(private val sneakersApi: Sneackers) {

    suspend fun getAllSneakers(): NetworkResponseSneakers<List<SneakersResponse>> {
        return try {
            val result = sneakersApi.getAllSneakers()
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun getPopularSneakers(): NetworkResponseSneakers<List<SneakersResponse>> {
        return try {
            val result = sneakersApi.getPopularSneakers()
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun getFavorites(): NetworkResponseSneakers<List<SneakersResponse>> {
        return try {
            val result = sneakersApi.getFavorites()
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun toggleFavorite(sneakerId: Int, isFavorite: Boolean): NetworkResponse<Unit> {
        return try {
            if (isFavorite) {
                sneakersApi.addToFavorites(sneakerId)
            } else {
                sneakersApi.removeFromFavorites(sneakerId)
            }
            NetworkResponse.Success(Unit)
        } catch (e: Exception) {
            NetworkResponse.Error(e.message ?: "Failed to toggle favorite")
        }
    }

    suspend fun getCart(): NetworkResponseSneakers<List<SneakersResponse>> {
        return try {
            val result = sneakersApi.getCart()
            NetworkResponseSneakers.Success(result)
        }
        catch (e: Exception){
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun addToCart(sneakerId: Int, inCart: Boolean): NetworkResponse<Unit> {
        return try {
            if(inCart){
                sneakersApi.addToCart(sneakerId)
            }
            NetworkResponse.Success(Unit)
        } catch (e:Exception) {
            NetworkResponse.Error(e.message ?: "Failed to add to cart")
        }
    }

    suspend fun removeFromCart(sneakerId: Int): NetworkResponse<Unit> {
        return try {
            sneakersApi.removeFromCart(sneakerId)
            NetworkResponse.Success(Unit)
        } catch (e: Exception) {
            NetworkResponse.Error(e.message ?: "Failed to remove from cart")
        }
    }



    suspend fun getSneakersByCategory(category: String): NetworkResponseSneakers<List<SneakersResponse>> {
        return try {
            val result = sneakersApi.getSneakersByCategory(category)
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }
}