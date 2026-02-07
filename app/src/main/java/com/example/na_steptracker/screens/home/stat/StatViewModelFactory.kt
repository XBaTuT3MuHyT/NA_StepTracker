package com.example.na_steptracker.screens.home.stat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.domain.WeatherRepository

class StatViewModelFactory(
    private val repository: StepsRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatViewModel::class.java)) {
            return StatViewModel(repository, weatherRepository) as T
        }
        error("Unknown ViewModel")
    }
}