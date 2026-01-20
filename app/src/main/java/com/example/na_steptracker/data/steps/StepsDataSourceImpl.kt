package com.example.na_steptracker.data.steps

import com.example.na_steptracker.data.db.Day
import com.example.na_steptracker.data.db.StepsDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class StepsDataSourceImpl(
    private val stepsDao: StepsDao
) : StepsDataSource {

    override suspend fun saveSteps(date: LocalDate, steps: Int) {
        stepsDao.insertSteps(
            Day(
                date,
                steps,
            )
        )
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
}
