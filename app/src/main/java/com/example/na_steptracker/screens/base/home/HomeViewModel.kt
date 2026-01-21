package com.example.na_steptracker.screens.base.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.na_steptracker.data.steps.StepsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class HomeViewModel(
    stepsRepository: StepsRepository
) : ViewModel() {

    private val today = LocalDate.now()

    private val dailyGoalFlow = flowOf(10_000)

    val dailyModel: StateFlow<DailyUiModel> =
        combine(
            stepsRepository.observeStepsForDate(today),
            dailyGoalFlow,
        ){ steps, goal ->
            DailyUiModel(
                current = steps.steps,
                target = goal,
                progress = steps.steps / goal.toFloat()
            )
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DailyUiModel(
                    0,
                    0,
                    0f
                )
            )
}
