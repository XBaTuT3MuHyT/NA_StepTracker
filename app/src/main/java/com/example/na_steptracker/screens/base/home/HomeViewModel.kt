package com.example.na_steptracker.screens.base.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.na_steptracker.data.steps.StepsRepository
import com.example.na_steptracker.domain.model.DaySteps
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class HomeViewModel(
    stepsRepository: StepsRepository
) : ViewModel() {
    private val today = LocalDate.now()

    val todaySteps: StateFlow<DaySteps> =
        stepsRepository.observeStepsForDate(today)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DaySteps(today, 0)
            )

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return super.create(modelClass)
            }
        }
    }
}