package com.example.na_steptracker.screens.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.na_steptracker.domain.StepsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class HomeViewModel(
    stepsRepository: StepsRepository
) : ViewModel() {

    private val today = LocalDate.now()

    private val dailyGoalFlow = flowOf(10_000)

    val dailyModel: StateFlow<DailyUiModel> = combine(
        stepsRepository.observeStepsForDate(today),
        dailyGoalFlow,
    ) { steps, goal ->
        DailyUiModel(
            current = steps.steps, target = goal, progress = steps.steps / goal.toFloat()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DailyUiModel(
            0, 0, 0f
        )
    )

    val weeklyModel: StateFlow<WeeklyModel> = combine(
        stepsRepository.observeStepsForPeriod(today.minusDays(6), today),
        dailyGoalFlow,
    ) { days, goal ->

        val averageSteps = days.map { it.steps }.average().toInt()

        val chartDays = days.map { day ->
            (day.steps.toFloat() / goal) to day.date.dayOfWeek.getDisplayName(
                TextStyle.SHORT, Locale.getDefault()
            )
        }

        WeeklyModel(
            averageSteps = averageSteps,
            days = chartDays,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WeeklyModel(
            0,
            emptyList(),
        )
    )
}
