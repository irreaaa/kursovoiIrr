package com.example.myapplication.ui.screen.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    val userName = authUseCase.localStorage.userNameFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
}

