package com.example.na_steptracker.data.prefs

import com.example.na_steptracker.screens.home.settings.AppLanguage
import kotlinx.coroutines.flow.Flow

interface PrefsDataSource {

    fun observeTodaySteps(): Flow<Int>

    fun observeThisHourSteps(): Flow<Int>

    fun observeName(): Flow<String>

    fun observeSurname(): Flow<String>

    fun observeGoal(): Flow<Int>

    fun observeLanguage(): Flow<String>

    suspend fun saveProfile(name: String, surname: String)

    suspend fun saveGoal(goal: Int)

    suspend fun saveLanguage(language: String)
}