package com.example.na_steptracker.data.steps

import com.example.na_steptracker.domain.model.DaySteps
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class StepsRepositoryImpl(
    val source: StepsDataSource
) : StepsRepository {
    override fun observeStepsForDate(
        date: LocalDate
    ): Flow<DaySteps> {
        return source.observeStepsForDate(date)
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
        return source.observeStepsForPeriod(from, to)
            .map { list ->
                list.mapIndexed { index, day ->
                    DaySteps(
                        date = day.date,
                        steps = day.steps
                    )
                }
            }
    }
}
