package com.example.na_steptracker.data.prefs.settingsPrefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class SettingsPrefsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SettingsPrefs {

    private object Keys {
        val NAME = stringPreferencesKey("name")
        val SURNAME = stringPreferencesKey("surname")
        val GOAL = intPreferencesKey("goal")
        val LANGUAGE = stringPreferencesKey("language")
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