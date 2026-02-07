package com.example.na_steptracker.data.weather

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "weathercode,temperature_2m",
    ): WeatherResponse

}