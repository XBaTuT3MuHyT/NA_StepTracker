package com.example.na_steptracker.presentation.screens.home.settings

import com.example.na_steptracker.domain.model.AppLanguage

data class SettingsUiState(
    val isLoading: Boolean = true,
    val profile: ProfileModel = ProfileModel(),
    val settings: SettingsModel = SettingsModel()
)
data class ProfileModel(
    val steps: Int = 0,
    val name: String = "Имя",
    val surname: String = "Фамилия",
    val isDirty: Boolean = false,
    val showDialog: Boolean = false,
)

data class SettingsModel(
    val selectedSteps: Int = 1000,
    val selectedLanguage: AppLanguage = AppLanguage.RU,
)
