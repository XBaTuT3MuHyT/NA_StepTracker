package com.example.na_steptracker.data.steps.daily

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StepsDataSource {

    suspend fun saveStepsDay(
        day: Day
    )

    fun observeStepsForDate(
        date: LocalDate,
        ownerId: Int,
    ): Flow<Day?>

    fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate,
        ownerId: Int,
    ): Flow<List<Day>>

    fun observeRecordSteps(
        ownerId: Int,
    ): Flow<List<Day>>

    fun observeRecordsWithWeather(
        ownerId: Int,
    ): Flow<List<DayWithWeatherEntity>>
}