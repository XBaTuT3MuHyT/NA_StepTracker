package com.example.na_steptracker.data.weather.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "weathercode,temperature_2m",
    ): WeatherResponse

    @GET("forecast")
    suspend fun getYesterdayWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("daily") current: String = "weathercode",
        @Query("past_days") pastDays: Int = 1,
        @Query("forecast_days") forecastDays: Int = 0,
    ): YesterdayWeatherResponse

}