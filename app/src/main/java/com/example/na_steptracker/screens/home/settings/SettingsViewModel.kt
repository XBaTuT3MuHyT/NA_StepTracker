package com.example.na_steptracker.screens.home.settings

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.na_steptracker.data.prefs.PrefsDataSource
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.graphs.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val stepsRepository: StepsRepository,
    private val navController: NavController,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.OnGoalSelected -> onGoalSelected(event.goal)
            is SettingsUiEvent.OnLanguageSelected -> onLanguageSelected(event.language)
            SettingsUiEvent.OnClickExit -> onClickExit()
            is SettingsUiEvent.OnNameValueChanged -> onNameValueChanged(event.content)
            is SettingsUiEvent.OnSurnameValueChanged -> onSurnameValueChanged(event.content)
        }
    }

    private fun onSurnameValueChanged(content: String) {
        _state.update { it.copy(profile = it.profile.copy(surname = content)) }
    }

    private fun onClickExit() {
        navController.navigate(Graph.AUTH) {
            popUpTo(0)
        }
    }

    private fun onLanguageSelected(language: AppLanguage) {
        _state.update { it.copy(settings = it.settings.copy(selectedLanguage = language)) }
    }

    private fun onNameValueChanged(content: String) {
        _state.update { it.copy(profile = it.profile.copy(name = content)) }
    }

    private fun onGoalSelected(goal: Int) {
        _state.update { it.copy(settings = it.settings.copy(selectedSteps = goal)) }
    }
}
