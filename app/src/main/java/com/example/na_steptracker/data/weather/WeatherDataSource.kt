package com.example.na_steptracker.data.weather

interface WeatherDataSource {
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeatherResponse
}