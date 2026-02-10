package com.example.na_steptracker.data.weather.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataSourceImpl @Inject constructor(
    private val api: WeatherApiService,
) : WeatherDataSource {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeatherResponse {
        return api.getCurrentWeather(lat, lon)
    }

    override suspend fun getYesterdayWeather(
        lat: Double,
        lon: Double
    ): YesterdayWeatherResponse {
        return api.getYesterdayWeather(lat, lon)
    }
}