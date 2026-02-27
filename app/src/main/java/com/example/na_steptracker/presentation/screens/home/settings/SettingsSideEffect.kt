package com.example.na_steptracker.presentation.screens.home.settings

import com.example.na_steptracker.presentation.screens.auth.signup.SignupSideEffect

sealed interface SettingsSideEffect {
    object NavigateToAuth: SettingsSideEffect
    object RecreateActivity : SettingsSideEffect
    data class ShowError(val message: String) : SettingsSideEffect
}
