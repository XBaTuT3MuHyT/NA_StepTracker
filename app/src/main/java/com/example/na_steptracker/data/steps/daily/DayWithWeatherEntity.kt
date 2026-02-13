package com.example.na_steptracker.data.steps.daily

import androidx.room.Embedded
import androidx.room.Relation
import com.example.na_steptracker.data.weather.local.WeatherEntity

data class DayWithWeatherEntity(
    @Embedded val day: Day,

    @Relation(
        parentColumn = "date",
        entityColumn = "date"
    )
    val weather: WeatherEntity?
)