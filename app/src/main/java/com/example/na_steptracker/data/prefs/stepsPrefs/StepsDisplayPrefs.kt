package com.example.na_steptracker.data.prefs.stepsPrefs

import kotlinx.coroutines.flow.Flow

interface StepsDisplayPrefs {

    val currentDateSteps: Flow<Int>

    val currentHourSteps: Flow<Int>

}