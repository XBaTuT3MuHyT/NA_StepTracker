package com.example.na_steptracker.data.weather

class WeatherDataSourceImpl(
    private val api: WeatherApiService,
) : WeatherDataSource {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeatherResponse {
        return api.getCurrentWeather(lat, lon)
    }
}