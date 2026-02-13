package com.example.na_steptracker.screens.home.home

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.na_steptracker.domain.model.Weather

data class DailyUiModel(
    val current: Int,
    val target: Int,
    val progress: Float,
    val weatherIcon: ImageVector,
)

data class WeeklyModel(
    val averageSteps: Int,
    val days: List<Pair<Float, String>>
)
