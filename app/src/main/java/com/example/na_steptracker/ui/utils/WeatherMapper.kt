package com.example.na_steptracker.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.ui.graphics.vector.ImageVector

object WeatherMapper {
    fun mapCodeToIcon(code: Int): ImageVector {
        return when (code) {
            0 -> Icons.Filled.WbSunny
            1 -> Icons.Filled.WbTwilight
            2, 3 -> Icons.Filled.Cloud
            45, 48 -> Icons.Filled.Water
            51, 53, 55 -> Icons.Filled.Grain
            61, 63, 65 -> Icons.Filled.WaterDrop
            71, 73, 75 -> Icons.Filled.AcUnit
            80, 81, 82 -> Icons.Filled.Thunderstorm
            95, 96, 99 -> Icons.Filled.ElectricBolt
            else -> Icons.Default.WbSunny
        }
    }
}