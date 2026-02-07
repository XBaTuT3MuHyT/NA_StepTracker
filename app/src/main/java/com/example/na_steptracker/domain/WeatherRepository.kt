package com.example.na_steptracker.domain

import com.example.na_steptracker.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface WeatherRepository {
    suspend fun fetchCurrentWeather(lat: Double, lon: Double): Weather

    suspend fun fetchYesterdayWeather(lat: Double, lon: Double): Int

    fun observeWeatherForDate(date: LocalDate): Flow<Weather?>

    suspend fun insertWeather(weatherCode: Int, date: LocalDate)
}