package com.example.na_steptracker.data.steps.hourly

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HourlyStepsDataSourceImpl @Inject constructor(
    private val hourlyStepsDao: HourlyStepsDao
) : HourlyStepsDataSource {

    override suspend fun saveStepsHour(hourlySteps: HourlySteps) {
        hourlyStepsDao.insertStepsForHour(hourlySteps)
    }

    override fun observeHourlySteps(): Flow<List<HourlySteps>> {
        return hourlyStepsDao.observeHourlySteps()
    }

    override suspend fun insertStepsForHour(hourlySteps: HourlySteps) {
        hourlyStepsDao.insertStepsForHour(hourlySteps)
    }

    override suspend fun deleteAll() {
        hourlyStepsDao.deleteAll()
    }
}