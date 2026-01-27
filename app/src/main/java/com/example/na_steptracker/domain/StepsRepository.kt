package com.example.na_steptracker.domain

import com.example.na_steptracker.domain.model.DaySteps
import com.example.na_steptracker.domain.model.HourSteps
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StepsRepository {
    fun observeStepsForDate(
        date: LocalDate
    ): Flow<DaySteps>
    fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<DaySteps>>
    fun observeHourlySteps(): Flow<List<HourSteps>>
    fun observeRecordSteps(): Flow<List<DaySteps>>
    suspend fun saveHour(
        hour: Int,
        steps: Int,
        )
    suspend fun saveDay(
        date: LocalDate,
        steps: Int,
    )
}