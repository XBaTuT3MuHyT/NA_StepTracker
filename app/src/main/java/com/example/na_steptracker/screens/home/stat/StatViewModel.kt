package com.example.na_steptracker.screens.home.stat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.na_steptracker.components.RecordCardModel
import com.example.na_steptracker.domain.StepsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.util.Locale

class StatViewModel(repository: StepsRepository): ViewModel() {

    private val today = LocalDate.now()

    private val firstDay = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    private val targetFlow = flowOf(10_000)

    val weeklyChartModel: StateFlow <WeeklyChartModel> = combine(
        repository.observeStepsForPeriod(firstDay, firstDay.plusDays(6)),
        targetFlow,
    ){ days, target ->
        val days = days.map { day ->
            ChartPoint(
                label = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                value = day.steps
            )
        }
        WeeklyChartModel(
            target = target,
            days
            )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WeeklyChartModel(
            0,
            emptyList()
        )
    )

    fun Int.toHourLabel(): String {
        return when (this) {
            in 0..23 -> String.format("%02d:00", this)
            else -> "??:??"
        }
    }

    val dailyChartModel: StateFlow <DailyChartModel> =
        repository.observeHourlySteps()
            .map { list ->
                val hours = list.map { hour ->
                    ChartPoint(
                        label = if (hour.hour != 0 && hour.hour % 6 == 0) hour.hour.toHourLabel() else null,
                        value = hour.steps
                    )
                }

                DailyChartModel(
                    hours = hours
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DailyChartModel(
                    emptyList()
                )
            )

    val records: StateFlow <RecordsModel> =
        repository.observeRecordSteps()
            .map { list ->
                val records = list.mapIndexed { index, day ->
                    RecordCardModel(
                        index = index,
                        weather = RecordCardModel.mock.weather,
                        steps = day.steps,
                        date = day.date,
                    )
                }
                RecordsModel(
                    records = records
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RecordsModel(emptyList())
            )


}
