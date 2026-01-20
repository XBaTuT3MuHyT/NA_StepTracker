package com.example.na_steptracker.data.steps

import kotlinx.coroutines.flow.Flow

interface StepSensorDataSource {
    fun observeTodaySteps(): Flow<Int>
}