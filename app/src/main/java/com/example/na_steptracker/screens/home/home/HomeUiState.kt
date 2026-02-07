package com.example.na_steptracker.screens.home.home

import com.example.na_steptracker.domain.model.Weather

data class DailyUiModel(
    val current: Int,
    val target: Int,
    val progress: Float,
    val weather: Weather,
)

data class WeeklyModel(
    val averageSteps: Int,
    val days: List<Pair<Float, String>>
)
