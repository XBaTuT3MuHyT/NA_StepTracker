package com.example.na_steptracker.data.weather.remote

interface WeatherDataSource {
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeatherResponse

    suspend fun getYesterdayWeather(
        lat: Double,
        lon: Double
    ): YesterdayWeatherResponse
}