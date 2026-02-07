package com.example.na_steptracker.data.weather.local

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LocalWeatherDataSource {
    fun observeWeatherForDate(date: LocalDate): Flow<WeatherEntity?>
    suspend fun insertWeather(weatherEntity: WeatherEntity)
}