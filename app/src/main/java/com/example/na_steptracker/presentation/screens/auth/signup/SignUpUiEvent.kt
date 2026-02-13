package com.example.na_steptracker.presentation.screens.auth.signup

sealed interface SignUpUiEvent {
    data class OnNameChanged(val name: String) : SignUpUiEvent
    data class OnEmailChanged(val email: String) : SignUpUiEvent
    data class OnPasswordChanged(val password: String) : SignUpUiEvent
    data class OnRepeatPasswordChanged(val repeatPassword: String) : SignUpUiEvent
    object OnSignUpClick : SignUpUiEvent
    object OnGoogleSignUpClick : SignUpUiEvent
    object OnLoginClick : SignUpUiEvent
    object OnForgotPasswordClick : SignUpUiEvent
    object OnVisibilityChange : SignUpUiEvent
    object OnVisibilityRepeatPasswordChange : SignUpUiEvent

}