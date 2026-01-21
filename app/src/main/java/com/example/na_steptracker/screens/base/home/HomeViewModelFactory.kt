package com.example.na_steptracker.screens.base.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.na_steptracker.data.steps.StepsRepository

class HomeViewModelFactory(
    private val repository: StepsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        error("Unknown ViewModel")
    }
}
