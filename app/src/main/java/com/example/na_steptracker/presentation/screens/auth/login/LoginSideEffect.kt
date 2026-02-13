package com.example.na_steptracker.presentation.screens.auth.login

sealed interface LoginSideEffect {
    object NavigateToHome : LoginSideEffect
    object NavigateToSignup : LoginSideEffect
    object NavigateToForgotPassword : LoginSideEffect

}