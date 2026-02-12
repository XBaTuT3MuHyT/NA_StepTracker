package com.example.na_steptracker.screens.auth.signup

data class SignupUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isRepeatPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,

)
