package com.example.na_steptracker.data.steps

import com.example.na_steptracker.data.prefs.PrefsDataSource
import com.example.na_steptracker.data.steps.daily.Day
import com.example.na_steptracker.data.steps.daily.StepsDataSource
import com.example.na_steptracker.data.steps.hourly.HourlySteps
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDataSource
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.domain.model.DaySteps
import com.example.na_steptracker.domain.model.HourSteps
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime

class StepsRepositoryImpl(
    val stepsSource: StepsDataSource,
    val hourlyStepsSource: HourlyStepsDataSource,
    val prefsDataSource: PrefsDataSource,
) : StepsRepository {

    fun currentHourFlow(): Flow<Int> = flow {
        while (true) {
            emit(LocalDateTime.now().hour)
            delay(1_000)
        }
    }.distinctUntilChanged()

    fun currentDateFlow(): Flow<LocalDate> = flow {
        while (true) {
            emit(LocalDate.now())
            delay(1_000)
        }
    }.distinctUntilChanged()

    override fun observeStepsForDate(
        date: LocalDate
    ): Flow<DaySteps> {
        return if (date == LocalDate.now()) {
            prefsDataSource.observeTodaySteps()
                .map { steps ->
                    DaySteps(
                        date = date,
                        steps = steps
                    )
                }
        } else {
            stepsSource.observeStepsForDate(date)
                .map { day ->
                    DaySteps(
                        date = date,
                        steps = day?.steps ?: 0,
                    )
                }
        }
    }

    override fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate
    ): Flow<List<DaySteps>> {

        return combine(
            stepsSource.observeStepsForPeriod(from, to),
            prefsDataSource.observeTodaySteps(),
            currentDateFlow(),
        ) { dbDays, todaySteps, today ->

            val dbMap = dbDays.associateBy { it.date }

            generateSequence(from) { it.plusDays(1) }
                .takeWhile { !it.isAfter(to) }
                .map { date ->
                    when {
                        date == today -> {
                            DaySteps(
                                date = date,
                                steps = todaySteps
                            )
                        }

                        else -> {
                            DaySteps(
                                date = date,
                                steps = dbMap[date]?.steps ?: 0
                            )
                        }
                    }
                }
                .toList()
        }
    }

    override fun observeHourlySteps(): Flow<List<HourSteps>> {
        return combine(
            hourlyStepsSource.observeHourlySteps(),
            prefsDataSource.observeThisHourSteps(),
            currentHourFlow(),
        ) { dbHours, thisHour, now ->
            val map = dbHours.associateBy { it.hour }

            (0..23).map { hour ->
                when {
                    hour == now -> {
                        HourSteps(
                            hour = now,
                            steps = thisHour
                        )
                    }

                    else -> {
                        HourSteps(
                            hour = hour,
                            steps = map[hour]?.steps ?: 0
                        )
                    }
                }

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

    override suspend fun deleteAllHours() {
        hourlyStepsSource.deleteAll()
    }
}
