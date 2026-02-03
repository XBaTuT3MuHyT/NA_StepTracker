package com.example.na_steptracker.data.prefs

import com.example.na_steptracker.data.prefs.settingsPrefs.SettingsPrefs
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefs
import kotlinx.coroutines.flow.Flow

class PrefsDataSourceImpl(
    private val stepsPrefs: StepsPrefs,
    private val settingsPrefs: SettingsPrefs
) : PrefsDataSource {
    override fun observeTodaySteps(): Flow<Int> {
        return stepsPrefs.currentDateSteps
    }

    override fun observeThisHourSteps(): Flow<Int> {
        return stepsPrefs.currentHourSteps
    }

    override fun observeName(): Flow<String> {
        return settingsPrefs.name
    }

    override fun observeSurname(): Flow<String> {
        return settingsPrefs.surname
    }

    override fun observeGoal(): Flow<Int> {
        return settingsPrefs.goal
    }

    override fun observeLanguage(): Flow<String> {
        return settingsPrefs.language
    }

    override suspend fun saveProfile(name: String, surname: String) {
        settingsPrefs.saveProfile(name, surname)
    }

    override suspend fun saveGoal(goal: Int) {
        settingsPrefs.saveGoal(goal)
    }

    override suspend fun saveLanguage(language: String) {
        settingsPrefs.saveLanguage(language)
    }
}
