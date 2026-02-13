package com.example.na_steptracker.domain

import com.example.na_steptracker.domain.model.DaySteps
import com.example.na_steptracker.domain.model.DayWithWeather
import com.example.na_steptracker.domain.model.HourSteps
import com.example.na_steptracker.domain.model.Profile
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StepsRepository {
    fun observeStepsForDate(
        date: LocalDate,
        ownerId: Int,
    ): Flow<DaySteps>
    fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate,
        ownerId: Int,
    ): Flow<List<DaySteps>>
    fun observeHourlySteps(): Flow<List<HourSteps>>
    fun observeRecordSteps(
        ownerId: Int,
    ): Flow<List<DayWithWeather>>
    fun observeProfile(): Flow<Profile>
    fun observeGoal(): Flow<Int>
    fun observeLanguage(): Flow<String>
    suspend fun saveHour(
        hour: Int,
        steps: Int,
        )
    suspend fun saveDay(
        date: LocalDate,
        steps: Int,
        ownerId: Int,
    )
    suspend fun saveProfile(profile: Profile)
    suspend fun saveGoal(goal: Int)
    suspend fun saveLanguage(language: String)
    suspend fun deleteAllHours()
}
