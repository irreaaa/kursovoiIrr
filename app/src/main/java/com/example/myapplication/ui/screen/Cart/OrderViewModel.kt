package com.example.myapplication.ui.screen.Cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.LocalStorage
import com.example.myapplication.domain.usecase.SendEmailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderConfirmationViewModel(
    private val localStorage: LocalStorage,
    private val sendEmailUseCase: SendEmailUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    init {
        viewModelScope.launch {
            _email.value = localStorage.emailFlow.first()
        }
    }

    fun sendConfirmationEmail(email: String, orderDetails: String) {
        viewModelScope.launch {
            sendEmailUseCase(email, orderDetails)
        }
    }
}
