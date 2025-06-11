package com.example.myapplication.ui.screen.Popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.FavoriteUseCase
import com.example.myapplication.domain.usecase.SneakersUseCase
import com.example.myapplication.data.remote.network.response.NetworkResponse
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.data.remote.network.response.SneakersResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopularViewModel(
    private val sneakersUseCase: SneakersUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _sneakersState = MutableStateFlow<NetworkResponseSneakers<List<SneakersResponse>>>(
        NetworkResponseSneakers.Loading)
    val sneakersState: StateFlow<NetworkResponseSneakers<List<SneakersResponse>>> = _sneakersState

    private val _favoritesState = MutableStateFlow<NetworkResponseSneakers<List<SneakersResponse>>>(
        NetworkResponseSneakers.Loading)
    val favoritesState: StateFlow<NetworkResponseSneakers<List<SneakersResponse>>> = _favoritesState

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
}