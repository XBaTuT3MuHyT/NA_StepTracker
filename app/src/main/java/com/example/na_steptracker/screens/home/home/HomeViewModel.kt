package com.example.na_steptracker.screens.home.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.domain.WeatherRepository
import com.example.na_steptracker.domain.model.Weather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class HomeViewModel(
    private val stepsRepository: StepsRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val today = LocalDate.now()
    private var DEFAULT_ICON =Icons.Filled.SignalWifiConnectedNoInternet4

    private val _weather = MutableStateFlow<Weather>(Weather(0.0,0, DEFAULT_ICON))
    val weather = _weather.asStateFlow()

    init {
        loadWeather(59.53, 29.54)
    }
    val dailyModel: StateFlow<DailyUiModel> = combine(
        stepsRepository.observeStepsForDate(today),
        stepsRepository.observeGoal(),
        weather
    ) { steps, goal, weather ->
        DailyUiModel(
            current = steps.steps,
            target = goal,
            progress = steps.steps / goal.toFloat(),
            weather = weather
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DailyUiModel(
            0, 0, 0f, Weather(0.0, 0, DEFAULT_ICON)
        )
    )

    val weeklyModel: StateFlow<WeeklyModel> = combine(
        stepsRepository.observeStepsForPeriod(today.minusDays(6), today),
        stepsRepository.observeGoal(),
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

    fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _weather.value = weatherRepository.fetchCurrentWeather(lat, lon)
                Log.d("retrofit", "${lat}")

            } catch (e: Exception) {
                Log.d("retrofit", "${e}")

            }
        }
    }
}
