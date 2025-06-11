package com.example.myapplication.domain.usecase

import com.example.myapplication.data.local.LocalStorage
import kotlinx.coroutines.flow.Flow

class OnBoardingUseCase(private val localStorage: LocalStorage)
{
    val isOnBoardingCompleted: Flow<Boolean>
        get() = localStorage.onBoardingShownFlow

    suspend fun completeOnBoarding() {
        localStorage.setOnBoardingShown(true)
    }
}