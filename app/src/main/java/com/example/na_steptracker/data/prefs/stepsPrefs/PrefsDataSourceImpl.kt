package com.example.na_steptracker.data.prefs.stepsPrefs

import kotlinx.coroutines.flow.Flow

class PrefsDataSourceImpl(
    private val stepsPrefs: StepsPrefs
) : PrefsDataSource {
    override fun observeTodaySteps(): Flow<Int> {
        return stepsPrefs.currentDateSteps
    }

    override fun observeThisHourSteps(): Flow<Int> {
        return stepsPrefs.currentHourSteps
    }
}