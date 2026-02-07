package com.example.na_steptracker.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Weather(
    val temp: Double,
    val weatherCode: Int,
    val icon: ImageVector,
)
