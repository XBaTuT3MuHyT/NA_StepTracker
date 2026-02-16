package com.example.na_steptracker.presentation.screens.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.na_steptracker.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<LoginSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()


    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnEmailChanged -> onEmailChanged(event.email)
            is LoginUiEvent.OnPasswordChanged -> onPasswordChanged(event.password)
            LoginUiEvent.OnLoginClick -> onLoginClick()
            LoginUiEvent.OnSignupClick -> onSignupClick()
            LoginUiEvent.OnForgotPasswordClick -> onForgotPasswordClick()
            LoginUiEvent.OnGoogleLoginClick -> onGoogleLoginClick()
            LoginUiEvent.OnVisibilityChange -> onVisibilityChange()
        }
    }

    private fun onVisibilityChange() {
        _state.value = _state.value.copy(
            isPasswordVisible = !_state.value.isPasswordVisible
        )
    }

    private fun onGoogleLoginClick() {
        TODO("Not yet implemented")
    }

    private fun onForgotPasswordClick() {
        viewModelScope.launch {
            _sideEffect.send(LoginSideEffect.NavigateToForgotPassword)
        }
    }

    private fun onSignupClick() {
        viewModelScope.launch {
            _sideEffect.send(LoginSideEffect.NavigateToSignup)
        }
    }

    private fun onLoginClick() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = authRepository.login(
                email = _state.value.email,
                passwordHash = _state.value.password.toSha256()
            )

            if (result) {
                authRepository.changeLoggedInState(true)
                _sideEffect.send(LoginSideEffect.NavigateToHome)

                val currentStatus = authRepository.isLoggedIn.filterNotNull().first()
                Log.d("startDestination", "Статус в DataStore обновился: $currentStatus")


                _state.update { it.copy(isLoading = false) }
            } else {
                _sideEffect.send(LoginSideEffect.ShowToast("Неверный логин или пароль"))
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onPasswordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }

}

fun String.toSha256(): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
}
