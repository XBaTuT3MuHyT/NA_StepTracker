package com.example.na_steptracker.screens.auth.login

sealed interface LoginUiEvent {
    data class OnEmailChanged(val email: String) : LoginUiEvent
    data class OnPasswordChanged(val password: String) : LoginUiEvent
    object OnLoginClick : LoginUiEvent
    object OnSignupClick : LoginUiEvent
    object OnForgotPasswordClick : LoginUiEvent
    object OnGoogleLoginClick : LoginUiEvent
    object OnVisibilityChange : LoginUiEvent
}
