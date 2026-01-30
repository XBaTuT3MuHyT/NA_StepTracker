package com.example.na_steptracker.screens.home.settings

import java.util.Locale

data class SettingsUiState(
    val profile: ProfileModel = ProfileModel(),
    val settings: SettingsModel = SettingsModel()
)
data class ProfileModel(
    val steps: Int = 0,
    val name: String = "Имя",
    val surname: String = "Фамилия",
    val isDirty: Boolean = false,
)

data class SettingsModel(
    val selectedSteps: Int = 1000,
    val selectedLanguage: AppLanguage = AppLanguage.RU,
)

enum class AppLanguage(
    val displayName: String,
    val locale: Locale
) {
    RU(
        displayName = "Русский",
        locale = Locale("ru")
    ),
    EN(
        displayName = "English",
        locale = Locale.ENGLISH
    );

    companion object {
        fun fromDisplayName(name: String): AppLanguage? {
            return entries.find { it.displayName == name }
        }
    }
}