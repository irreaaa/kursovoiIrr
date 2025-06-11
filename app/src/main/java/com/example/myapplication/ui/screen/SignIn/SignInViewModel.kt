package com.example.myapplication.ui.screen.SignIn

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.AuthUseCase
import com.example.myapplication.data.remote.network.request.LoginRequest
import com.example.myapplication.data.remote.network.response.NetworkResponse
import kotlinx.coroutines.launch

class SignInViewModel(val authUseCase: AuthUseCase): ViewModel() {
    var signInState = mutableStateOf(SignInState())
        private set

    val emailHasError = derivedStateOf {
        if(signInState.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.value.email).matches()
    }

    fun setEmail(email: String){
        signInState.value = signInState.value.copy(email = email)
    }

    fun setPassword(password: String){
        signInState.value = signInState.value.copy(password = password)
    }

    private fun setErrorMessage(message: String) {
        signInState.value = signInState.value.copy(errorMessage = message)
    }

    private fun setLoading(isLoading: Boolean) {
        signInState.value = signInState.value.copy(isLoading = true)
    }

    fun signIn() {
        viewModelScope.launch {
            val loginRequest = LoginRequest(
                email = signInState.value.email,
                password = signInState.value.password
            )
            val result = authUseCase.signIn(loginRequest)
            result.collect{ it ->
                when(it){
                    is NetworkResponse.Error ->{
                        setErrorMessage(it.errorMessage)
                    }
                    is NetworkResponse.Success<*> -> {
                        setLoading(false)
                        signInState.value = signInState.value.copy(isSignIn = true)
                    }
                    is NetworkResponse.Loading -> {
                        setLoading(true)
                    }
                }
            }
        }
    }
}