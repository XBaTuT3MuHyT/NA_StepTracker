package com.example.na_steptracker.screens.home.stat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.na_steptracker.domain.StepsRepository

class StatViewModelFactory(
    private val repository: StepsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatViewModel::class.java)) {
            return StatViewModel(repository) as T
        }
        error("Unknown ViewModel")
    }
}