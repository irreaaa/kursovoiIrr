package com.example.myapplication.ui.screen.Popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.FavoriteUseCase
import com.example.myapplication.domain.usecase.SneakersUseCase
import com.example.myapplication.data.remote.network.response.NetworkResponse
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.data.remote.network.response.SneakersResponse
import com.example.myapplication.data.repository.SneakersRepository
import com.example.myapplication.domain.usecase.CartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopularViewModel(
    private val sneakersUseCase: SneakersUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    private val cartUseCase: CartUseCase,
    private val sneakersRepository: SneakersRepository

) : ViewModel() {

    private val _sneakersState = MutableStateFlow<NetworkResponseSneakers<List<SneakersResponse>>>(
        NetworkResponseSneakers.Loading)
    val sneakersState: StateFlow<NetworkResponseSneakers<List<SneakersResponse>>> = _sneakersState

    private val _favoritesState = MutableStateFlow<NetworkResponseSneakers<List<SneakersResponse>>>(
        NetworkResponseSneakers.Loading)
    val favoritesState: StateFlow<NetworkResponseSneakers<List<SneakersResponse>>> = _favoritesState

    private val _cartState = MutableStateFlow<NetworkResponseSneakers<List<SneakersResponse>>>(
        NetworkResponseSneakers.Loading)
    val cartState: StateFlow<NetworkResponseSneakers<List<SneakersResponse>>> = _cartState

    private var currentCategory: String = "Все"

    fun fetchFavorites() {
        viewModelScope.launch {
            val result = favoriteUseCase.getFavorites()
            if (result is NetworkResponseSneakers.Success) {
                val modified = result.data.map { it.copy(isFavorite = true) }
                _favoritesState.value = NetworkResponseSneakers.Success(modified)
            } else {
                _favoritesState.value = result
            }
        }
    }

    fun fetchCart() {
        viewModelScope.launch {
            val result = cartUseCase.getCart()
            if (result is NetworkResponseSneakers.Success) {
                val modified = result.data.map { it.copy(inCart = true) }
                _cartState.value = NetworkResponseSneakers.Success(modified)
            } else {
                _cartState.value = result
            }
        }
    }

    fun fetchSneakersByCategory(category: String) {
        currentCategory = category
        viewModelScope.launch {
            _sneakersState.value = NetworkResponseSneakers.Loading
            _sneakersState.value = mergeSneakersWithFavorites(category)
        }
    }

    fun toggleFavorite(sneakerId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            val result = favoriteUseCase.toggleFavorite(sneakerId, isFavorite)
            if (result is NetworkResponse.Success) {
                fetchFavorites()
                fetchSneakersByCategory(currentCategory)
            }
        }
    }

    fun toggleCart(sneakerId: Int, inCart: Boolean) {
        viewModelScope.launch {
            try {
                if (inCart) {
                    sneakersRepository.addToCart(sneakerId, true)
                } else {
                    sneakersRepository.removeFromCart(sneakerId, false)
                }
                fetchCart()
            } catch (e: Exception) {

            }
        }
    }


    private suspend fun mergeSneakersWithFavorites(category: String): NetworkResponseSneakers<List<SneakersResponse>> {
        val allSneakersResult = sneakersUseCase.getAllSneakers()
        val favoritesResult = favoriteUseCase.getFavorites()

        if (allSneakersResult is NetworkResponseSneakers.Success &&
            favoritesResult is NetworkResponseSneakers.Success) {

            val allSneakers = allSneakersResult.data
            val favoriteIds = favoritesResult.data.map { it.id }.toSet()

            val merged = allSneakers.map {
                it.copy(isFavorite = it.id in favoriteIds)
            }

            val filtered = when (category) {
                "Все" -> merged
                "Популярное" -> merged.filter { it.isPopular }
                else -> merged.filter { it.category.equals(category, ignoreCase = true) }
            }

            return NetworkResponseSneakers.Success(filtered)
        }

        return NetworkResponseSneakers.Error("Ошибка при получении данных с сервера")
    }

    private suspend fun mergeSneakersWithCart(category: String): NetworkResponseSneakers<List<SneakersResponse>> {
        val allSneakersResult = sneakersUseCase.getAllSneakers()
        val cartResult = cartUseCase.getCart()
        if (allSneakersResult is NetworkResponseSneakers.Success &&
            cartResult is NetworkResponseSneakers.Success) {

            val allSneakers = allSneakersResult.data
            val cartIds = cartResult.data.map { it.id }.toSet()

            val merged = allSneakers.map {
                it.copy(inCart = it.id in cartIds)
            }

            val filtered = when (category) {
                "Все" -> merged
                "Популярное" -> merged.filter { it.isPopular }
                else -> merged.filter { it.category.equals(category, ignoreCase = true) }
            }

            return NetworkResponseSneakers.Success(filtered)
        }

        return NetworkResponseSneakers.Error("Ошибка при получении данных с сервера")
    }
}