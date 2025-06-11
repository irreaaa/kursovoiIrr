package com.example.myapplication.domain.usecase

import com.example.myapplication.data.local.LocalStorage
import kotlinx.coroutines.flow.Flow

class TokenUseCase(private val localStorage: LocalStorage) {
    val token: Flow<String>
        get() = localStorage.tokenFlow
}
