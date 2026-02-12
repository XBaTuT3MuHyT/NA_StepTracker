package com.example.na_steptracker.screens.auth.signup

sealed interface SignupSideEffect {
    object NavigateToHome : SignupSideEffect
    object NavigateToLogin : SignupSideEffect
    object NavigateToForgotPassword : SignupSideEffect
}