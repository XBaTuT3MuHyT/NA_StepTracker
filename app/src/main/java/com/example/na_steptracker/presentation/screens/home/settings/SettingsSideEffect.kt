package com.example.na_steptracker.presentation.screens.home.settings

sealed interface SettingsSideEffect {
    object NavigateToAuth: SettingsSideEffect
}