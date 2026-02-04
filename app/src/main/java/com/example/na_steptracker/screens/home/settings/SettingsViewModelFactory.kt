package com.example.na_steptracker.screens.home.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.screens.home.home.HomeViewModel

class SettingsViewModelFactory (
    private val stepsRepository: StepsRepository,
    private val navController: NavController,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(stepsRepository, navController) as T
        }
        error("Unknown ViewModel")
    }
}
