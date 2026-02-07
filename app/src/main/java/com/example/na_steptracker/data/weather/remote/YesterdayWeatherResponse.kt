package com.example.na_steptracker.data.weather.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YesterdayWeatherResponse(
    @Json(name = "daily")
    val daily: DailyWeatherData
)

@JsonClass(generateAdapter = true)
data class DailyWeatherData(
    @Json(name = "weathercode")
    val code: List<Int>
)