package com.example.na_steptracker.data.steps

import com.example.na_steptracker.data.prefs.stepsPrefs.PrefsDataSource
import com.example.na_steptracker.data.steps.daily.Day
import com.example.na_steptracker.data.steps.daily.StepsDao
import com.example.na_steptracker.data.steps.daily.StepsDataSource
import com.example.na_steptracker.data.steps.hourly.HourlySteps
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDao
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDataSource
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.domain.model.DaySteps
import com.example.na_steptracker.domain.model.HourSteps
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class StepsRepositoryImpl(
    val stepsSource: StepsDataSource,
    val hourlyStepsSource: HourlyStepsDataSource,
    val prefsDataSource: PrefsDataSource,
) : StepsRepository {

    override fun observeStepsForDate(
        date: LocalDate
    ): Flow<DaySteps> {
        return stepsSource.observeStepsForDate(date)
            .map { day ->
                DaySteps(
                    date = date,
                    steps = day?.steps ?: 0,
                )
            }
    }

    override fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate
    ): Flow<List<DaySteps>> {
        return stepsSource.observeStepsForPeriod(from, to)
            .map { list ->

                val map = list.associateBy { it.date }

                generateSequence(from) { it.plusDays(1) }
                    .takeWhile { !it.isAfter(to) }
                    .map { day ->
                        DaySteps(
                            date = day,
                            steps = map[day]?.steps ?: 0
                        )
                    }
                    .toList()
            }
    }

    override fun observeHourlySteps(): Flow<List<HourSteps>> {
        return hourlyStepsSource.observeHourlySteps()
            .map { list ->

                val map = list.associateBy { it.hour }

                (0..23).map { hour ->
                    HourSteps(
                        hour = hour,
                        steps = map[hour]?.steps ?: 0
                    )
                }
                    .toList()
            }
    }

    override fun observeRecordSteps(): Flow<List<DaySteps>> {
        return stepsSource.observeRecordSteps()
            .map { list ->
                list.map { day ->
                    DaySteps(
                        date = day.date,
                        steps = day.steps
                    )
                }
            }
    }

    override suspend fun saveHour(hour: Int, steps: Int) {
        hourlyStepsSource.insertStepsForHour(
            HourlySteps(
                hour = hour,
                steps = steps
            )
        )
    }

    override suspend fun saveDay(date: LocalDate, steps: Int) {
        stepsSource.saveStepsDay(
            Day(
                date = date,
                steps = steps
            )
        )
    }
}
