package com.example.na_steptracker.data.prefs.settingsPrefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.na_steptracker.data.prefs.PrefsImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPrefsImpl(
    context: Context
): SettingsPrefs {

    val Context.stepsDataStore by preferencesDataStore(
        name = "settings_store"
    )
    private val dataStore = context.stepsDataStore

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