package com.example.na_steptracker.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "current")
    val current: CurrentWeatherData
)

@JsonClass(generateAdapter = true)
data class CurrentWeatherData(
    @Json(name = "temperature_2m")
    val temperature: Double,

    @Json(name = "weathercode")
    val code: Int
)