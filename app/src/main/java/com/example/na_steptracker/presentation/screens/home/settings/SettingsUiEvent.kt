package com.example.na_steptracker.presentation.screens.home.settings

import com.example.na_steptracker.domain.model.AppLanguage

sealed interface SettingsUiEvent {
    data class OnGoalSelected(val goal: Int) : SettingsUiEvent
    data class OnLanguageSelected(val language: AppLanguage): SettingsUiEvent
    data class OnNameValueChanged(val content: String): SettingsUiEvent
    data class OnSurnameValueChanged(val content: String): SettingsUiEvent
    object OnClickExit: SettingsUiEvent
    object OnApply: SettingsUiEvent
    object OnDismiss: SettingsUiEvent
    object OnClickProfile: SettingsUiEvent
}