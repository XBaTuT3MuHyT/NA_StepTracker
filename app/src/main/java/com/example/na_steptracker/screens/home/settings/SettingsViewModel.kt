package com.example.na_steptracker.screens.home.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.domain.model.AppLanguage
import com.example.na_steptracker.domain.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val stepsRepository: StepsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsUiState())
    val state = _state.asStateFlow()
    private val _effect = Channel<SettingsSideEffect>()
    val effect = _effect.receiveAsFlow()

    private var lastSavedProfile: Profile? = null

    init {
        viewModelScope.launch {
            combine(
                stepsRepository.observeGoal(),
                stepsRepository.observeLanguage(),
                stepsRepository.observeProfile(),
            ) { goal, language, profile ->
                updateState(goal, language, profile)
            }.collect()
        }
    }

    private fun updateState(goal: Int, language: String, profile: Profile) {
        lastSavedProfile = profile

        _state.update { currentState ->
            currentState.copy(
                isLoading = false,
                profile = if (currentState.profile.isDirty) {
                    currentState.profile
                } else {
                    ProfileModel(
                        name = profile.name,
                        surname = profile.surname,
                        isDirty = false
                    )
                },
                settings = currentState.settings.copy(
                    selectedSteps = goal,
                    selectedLanguage = AppLanguage.fromDisplayName(language)
                )
            )
        }
    }

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.OnGoalSelected -> onGoalSelected(event.goal)
            is SettingsUiEvent.OnLanguageSelected -> onLanguageSelected(event.language)
            SettingsUiEvent.OnClickExit -> onClickExit()
            is SettingsUiEvent.OnNameValueChanged -> onNameValueChanged(event.content)
            is SettingsUiEvent.OnSurnameValueChanged -> onSurnameValueChanged(event.content)
            SettingsUiEvent.OnApply -> onClickApply()
            SettingsUiEvent.OnDismiss -> onDismiss()
            SettingsUiEvent.OnClickProfile -> onClickProfile()
        }
    }

    private fun onClickProfile() {
        _state.update { it.copy(profile = it.profile.copy(showDialog = true)) }
    }

    private fun onDismiss() {
        _state.update { currentState ->
            val restoredProfile = lastSavedProfile?.let { saved ->
                ProfileModel(
                    name = saved.name,
                    surname = saved.surname,
                    isDirty = false,
                    showDialog = false,
                )
            } ?: currentState.profile.copy(isDirty = false, showDialog = false)

            currentState.copy(profile = restoredProfile)
        }
    }

    private fun onClickApply() {
        _state.update { it.copy(profile = it.profile.copy(isDirty = false, showDialog = false)) }
        viewModelScope.launch {
            try {
                stepsRepository.saveProfile(
                    Profile(
                        name = _state.value.profile.name,
                        surname = _state.value.profile.surname,
                    )
                )
            } catch (e: Exception) {
            }
        }
    }

    private fun onSurnameValueChanged(content: String) {
        _state.update { it.copy(profile = it.profile.copy(surname = content, isDirty = true)) }
    }

    private fun onClickExit() {
        viewModelScope.launch {
            _effect.send(SettingsSideEffect.NavigateToAuth)
        }
    }

    private fun onLanguageSelected(language: AppLanguage) {
        _state.update { it.copy(settings = it.settings.copy(selectedLanguage = language)) }
        viewModelScope.launch {
            try {
                stepsRepository.saveLanguage(language.displayName)
            } catch (e: Exception) {
            }
        }
    }

    private fun onNameValueChanged(content: String) {
        _state.update { it.copy(profile = it.profile.copy(name = content, isDirty = true)) }
    }

    private fun onGoalSelected(goal: Int) {
        _state.update { it.copy(settings = it.settings.copy(selectedSteps = goal)) }
        viewModelScope.launch {
            try {
                stepsRepository.saveGoal(goal)
            } catch (e: Exception) {
            }
        }
    }
}
