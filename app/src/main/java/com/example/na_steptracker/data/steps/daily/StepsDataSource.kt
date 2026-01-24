package com.example.na_steptracker.data.steps.daily

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StepsDataSource {

    suspend fun saveSteps(
        date: LocalDate,
        steps: Int,
    )

    fun observeStepsForDate(
        date: LocalDate
    ): Flow<Day?>

    fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate
    ): Flow<List<Day>>

    fun observeRecordSteps(): Flow<List<Day>>
}