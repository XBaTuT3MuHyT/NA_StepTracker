package com.example.na_steptracker.screens.home.settings

sealed interface SettingsSideEffect {
    object NavigateToAuth: SettingsSideEffect
}