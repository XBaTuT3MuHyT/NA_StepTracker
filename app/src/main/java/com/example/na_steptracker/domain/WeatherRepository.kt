package com.example.na_steptracker.domain

import com.example.na_steptracker.data.weather.WeatherResponse
import com.example.na_steptracker.domain.model.Weather

interface WeatherRepository {
    suspend fun fetchCurrentWeather(lat: Double, lon: Double): Weather
}