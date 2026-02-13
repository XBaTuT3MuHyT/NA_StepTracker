package com.example.na_steptracker.presentation.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.na_steptracker.data.auth.User
import com.example.na_steptracker.domain.AuthRepository
import com.example.na_steptracker.presentation.screens.auth.login.toSha256
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(SignupUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<SignupSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: SignUpUiEvent) {
        when (event) {
            is SignUpUiEvent.OnEmailChanged -> onEmailChanged(event.email)
            SignUpUiEvent.OnForgotPasswordClick -> onForgotPasswordClick()
            SignUpUiEvent.OnGoogleSignUpClick -> onGoogleSignUpClick()
            SignUpUiEvent.OnLoginClick -> onLoginClick()
            is SignUpUiEvent.OnNameChanged -> onNameChanged(event.name)
            is SignUpUiEvent.OnPasswordChanged -> onPasswordChanged(event.password)
            is SignUpUiEvent.OnRepeatPasswordChanged -> onRepeatPasswordChanged(event.repeatPassword)
            SignUpUiEvent.OnSignUpClick -> onSignUpClick()
            SignUpUiEvent.OnVisibilityChange -> onVisibilityChange()
            SignUpUiEvent.OnVisibilityRepeatPasswordChange -> onVisibilityRepeatPasswordChange()
        }
    }

    private fun onVisibilityRepeatPasswordChange() {
        _state.value = _state.value.copy(
            isRepeatPasswordVisible = !_state.value.isRepeatPasswordVisible
        )
    }

    private fun onVisibilityChange() {
        _state.value = _state.value.copy(
            isPasswordVisible = !_state.value.isPasswordVisible
        )
    }

    private fun onSignUpClick() {
        val currentState = _state.value
        
        if (currentState.password != currentState.repeatPassword) {
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val passwordHash = currentState.password.toSha256()
                
                val newUser = User(
                    name = currentState.name,
                    email = currentState.email,
                    passwordHash = passwordHash
                )
                authRepository.registerUser(newUser)

                val loginResult = authRepository.login(
                    email = currentState.email,
                    passwordHash = passwordHash
                )

                if (loginResult) {
                    authRepository.changeLoggedInState(true)
                    _sideEffect.send(SignupSideEffect.NavigateToHome)
                } else {
                    _state.update { it.copy(isLoading = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onRepeatPasswordChanged(repeatPassword: String) {
        _state.update { it.copy(repeatPassword = repeatPassword) }
    }

    private fun onPasswordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun onNameChanged(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }

    private fun onLoginClick() {
        viewModelScope.launch {
            _sideEffect.send(SignupSideEffect.NavigateToLogin)
        }
    }

    private fun onGoogleSignUpClick() {

    }

    private fun onForgotPasswordClick() {
        viewModelScope.launch {
            _sideEffect.send(SignupSideEffect.NavigateToForgotPassword)
        }
    }
}
