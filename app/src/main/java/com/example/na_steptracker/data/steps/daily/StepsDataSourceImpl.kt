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

    override fun observeStepsForDate(
        date: LocalDate,
        ownerId: Int,
    ): Flow<Day?> {
        return stepsDao.observeStepsForDate(date, ownerId)
    }

    override fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate,
        ownerId: Int,
    ): Flow<List<Day>> {
        return stepsDao.observeStepsForPeriod(from, to, ownerId)
    }

    override fun observeRecordSteps(
        ownerId: Int,
    ): Flow<List<Day>> {
        return stepsDao.observeRecordSteps(ownerId)
    }

    override fun observeRecordsWithWeather(
        ownerId: Int,
    ): Flow<List<DayWithWeatherEntity>> {
        return stepsDao.observeRecordDaysWithWeather(ownerId)
    }

    override fun observeAllSteps(ownerId: Int): Flow<List<Day>> {
        return stepsDao.observeAllSteps(ownerId)
    }

}