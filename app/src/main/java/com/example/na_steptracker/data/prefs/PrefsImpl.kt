package com.example.na_steptracker.data.prefs

import android.content.Context
import androidx.compose.ui.input.key.Key
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.na_steptracker.data.prefs.settingsPrefs.SettingsPrefs
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime

class PrefsImpl(
    context: Context
) : StepsPrefs, SettingsPrefs {

    val Context.stepsDataStore by preferencesDataStore(
        name = "steps_store"
    )
    private val dataStore = context.stepsDataStore

    private object Keys {
        val LAST_SENSOR_STEPS = intPreferencesKey("last_sensor_steps")
        val LAST_HOUR = intPreferencesKey("last_hour")
        val LAST_DATE = stringPreferencesKey("last_date")
        val HOUR_STEPS = intPreferencesKey("hour_steps")
        val DATE_STEPS = intPreferencesKey("date_steps")
        val NAME = stringPreferencesKey("name")
        val SURNAME = stringPreferencesKey("surname")
        val GOAL = intPreferencesKey("goal")
        val LANGUAGE = stringPreferencesKey("language")
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

    override val goal: Flow<Int> =
        dataStore.data.map { pref ->
            pref[Keys.GOAL]?: 0
        }

    override val language: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[Keys.LANGUAGE]?: ""
        }
    override val name: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[Keys.NAME]?: ""
        }

    override val surname: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[Keys.SURNAME]?: ""
        }

    override suspend fun saveProfile(name: String, surname: String) {
        dataStore.edit { preferences ->
            preferences[Keys.SURNAME] = surname
            preferences[Keys.NAME] = name
        }
    }

    override suspend fun saveGoal(goal: Int) {
        dataStore.edit { preferences ->
            preferences[Keys.GOAL] = goal
        }
    }

    override suspend fun saveLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[Keys.LANGUAGE] = language
        }
    }
}