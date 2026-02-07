package com.example.na_steptracker.screens.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.domain.WeatherRepository

class HomeViewModelFactory(
    private val stepsRepository: StepsRepository,
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                stepsRepository = stepsRepository,
                weatherRepository = weatherRepository,
            ) as T
        }
        error("Unknown ViewModel")
    }
}
