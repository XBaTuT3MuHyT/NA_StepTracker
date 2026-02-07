package com.example.na_steptracker.data.weather.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "weather",
    indices = [Index(value = ["date"], unique = true)])
data class WeatherEntity(
    @PrimaryKey val date: LocalDate,
    val weatherCode: Int,
)
