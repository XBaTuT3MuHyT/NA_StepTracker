package com.example.na_steptracker.data.prefs.stepsPrefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime

@Singleton
class StepsPrefsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): StepsDisplayPrefs, StepsPrefs {

    private object Keys {
        val LAST_SENSOR_STEPS = intPreferencesKey("last_sensor_steps")
        val LAST_HOUR = intPreferencesKey("last_hour")
        val LAST_DATE = stringPreferencesKey("last_date")
        val HOUR_STEPS = intPreferencesKey("hour_steps")
        val DATE_STEPS = intPreferencesKey("date_steps")
    }

    override val lastSensorValue: Flow<Int?> =
        dataStore.data.map { pref ->
            pref[Keys.LAST_SENSOR_STEPS]
        }
    override val lastSavedDate: Flow<LocalDate> =
        dataStore.data.map { pref ->
            pref[Keys.LAST_DATE]?.let(LocalDate::parse) ?: LocalDate.now()
        }
    override val lastSavedHour: Flow<Int> =
        dataStore.data.map { pref ->
            pref[Keys.LAST_HOUR] ?: LocalDateTime.now().hour
        }
    override val currentDateSteps: Flow<Int> =
        dataStore.data.map { pref ->
            pref[Keys.DATE_STEPS] ?: 0
        }
    override val currentHourSteps: Flow<Int> =
        dataStore.data.map { pref ->
            pref[Keys.HOUR_STEPS] ?: 0
        }

    override suspend fun saveLastSensorValue(value: Int) {
        dataStore.edit { prefs ->
            prefs[Keys.LAST_SENSOR_STEPS] = value
        }
    }

    override suspend fun saveSnapshot(
        lastSensorValue: Int,
        currentDateSteps: Int,
        currentHourSteps: Int
    ) {
        dataStore.edit { prefs ->
            prefs[Keys.LAST_SENSOR_STEPS] = lastSensorValue
            prefs[Keys.DATE_STEPS] = currentDateSteps
            prefs[Keys.HOUR_STEPS] = currentHourSteps
        }
    }

    override suspend fun saveHour(
        lastSensorValue: Int,
        lastSavedHour: Int,
        currentHourSteps: Int
    ) {
        dataStore.edit { prefs ->
            prefs[Keys.LAST_SENSOR_STEPS] = lastSensorValue
            prefs[Keys.LAST_HOUR] = lastSavedHour
            prefs[Keys.HOUR_STEPS] = currentHourSteps
        }
    }

    override suspend fun saveDay(
        lastSensorValue: Int,
        lastSavedDate: LocalDate,
        currentDateSteps: Int
    ) {
        dataStore.edit { prefs ->
            prefs[Keys.LAST_SENSOR_STEPS] = lastSensorValue
            prefs[Keys.LAST_DATE] = lastSavedDate.toString()
            prefs[Keys.DATE_STEPS] = currentDateSteps
        }
    }
}
