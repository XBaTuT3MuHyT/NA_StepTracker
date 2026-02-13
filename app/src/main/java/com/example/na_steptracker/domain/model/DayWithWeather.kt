package com.example.na_steptracker.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate

data class DayWithWeather(
    val date: LocalDate,
    val steps: Int,
    val weatherCode: Int,
)
