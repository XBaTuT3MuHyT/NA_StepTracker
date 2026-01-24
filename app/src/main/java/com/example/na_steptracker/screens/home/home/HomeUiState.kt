package com.example.na_steptracker.screens.home.home

class HomeUiState {
}

data class DailyUiModel(
    val current: Int,
    val target: Int,
    val progress: Float,
)

data class WeeklyModel(
    val averageSteps: Int,
    val days: List<Pair<Float, String>>
)