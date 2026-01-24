package com.example.na_steptracker.data.steps.hourly

import kotlinx.coroutines.flow.Flow

interface HourlyStepsDataSource {

    fun observeHourlySteps() : Flow<List<HourlySteps>>

    suspend fun insertStepsForHour(hourlySteps: HourlySteps)
}