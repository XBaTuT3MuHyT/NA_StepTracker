package com.example.na_steptracker.data.prefs.settingsPrefs

import kotlinx.coroutines.flow.Flow

interface SettingsPrefs {
    val goal: Flow<Int>
    val language: Flow<String>
    val name: Flow<String>
    val surname: Flow<String>
    val isLoggedIn: Flow<Boolean>
    val currentUserId: Flow<Int?>

    suspend fun saveProfile(
        name: String,
        surname: String,
    )

    suspend fun saveGoal(
        goal: Int
    )

    suspend fun saveLanguage(
        language: String
    )

    suspend fun saveIsLoggedIn(
        isLoggedIn: Boolean
    )

    suspend fun saveCurrentUserId(
        id: Int
    )

}
