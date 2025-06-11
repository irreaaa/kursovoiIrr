package com.example.myapplication.ui.screen.Welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.OnBoardingUseCase
import com.example.myapplication.domain.usecase.TokenUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashViewModel(
    private val tokenUseCase: TokenUseCase,
    private val onBoardingUseCase: OnBoardingUseCase
) : ViewModel() {

    val token = tokenUseCase.token
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val isOnBoardingCompleted = onBoardingUseCase.isOnBoardingCompleted
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun completeOnBoarding() {
        viewModelScope.launch {
            onBoardingUseCase.completeOnBoarding()
        }
    }
}
