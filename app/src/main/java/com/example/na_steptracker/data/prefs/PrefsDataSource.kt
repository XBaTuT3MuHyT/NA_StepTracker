package com.example.na_steptracker.data.prefs

import kotlinx.coroutines.flow.Flow

interface PrefsDataSource {

    fun observeTodaySteps(): Flow<Int>

    fun observeThisHourSteps(): Flow<Int>
}