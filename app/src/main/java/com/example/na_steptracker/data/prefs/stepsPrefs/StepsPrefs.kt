package com.example.na_steptracker.data.prefs.stepsPrefs

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

val Context.stepsDataStore by preferencesDataStore(
    name = "steps_store"
)
interface StepsPrefs {
    val lastSensorValue: Flow<Int?>
    val lastSavedDate: Flow<LocalDate>
    val lastSavedHour: Flow<Int>
    val currentDateSteps: Flow<Int>
    val currentHourSteps: Flow<Int>

    suspend fun saveLastSensorValue(value: Int)

    suspend fun saveSnapshot(
        lastSensorValue: Int,
        currentDateSteps: Int,
        currentHourSteps: Int,
    )
    suspend fun saveHour(
        lastSensorValue: Int,
        lastSavedHour: Int,
        currentHourSteps: Int,
    )
    suspend fun saveDay(
        lastSensorValue: Int,
        lastSavedDate: LocalDate,
        currentDateSteps: Int,
    )
}
