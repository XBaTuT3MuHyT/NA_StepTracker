package com.example.na_steptracker.data.steps.daily

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepsDataSourceImpl @Inject constructor(
    private val stepsDao: StepsDao
) : StepsDataSource {

    override suspend fun saveStepsDay(day: Day) {
        stepsDao.insertSteps(day)
    }

    override fun observeStepsForDate(date: LocalDate): Flow<Day?> {
        return stepsDao.observeStepsForDate(date)
    }

    override fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate
    ): Flow<List<Day>> {
        return stepsDao.observeStepsForPeriod(from, to)
    }

    override fun observeRecordSteps(): Flow<List<Day>> {
        return stepsDao.observeRecordSteps()
    }

    override fun observeRecordsWithWeather(): Flow<List<DayWithWeatherEntity>> {
        return stepsDao.observeRecordDaysWithWeather()
    }
}