package com.example.na_steptracker.screens.home.stat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.na_steptracker.components.RecordCardModel
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.domain.WeatherRepository
import com.example.na_steptracker.ui.utils.WeatherMapper
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.util.Locale

class StatViewModel(
    private val repository: StepsRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val today = LocalDate.now()

    private val firstDay = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    val weeklyChartModel: StateFlow<WeeklyChartModel> = combine(
        repository.observeStepsForPeriod(firstDay, firstDay.plusDays(6)),
        repository.observeGoal(),
    ) { days, target ->
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

    val dailyChartModel: StateFlow<DailyChartModel> =
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

    val records: StateFlow<RecordsModel> = repository.observeRecordSteps()
        .map { list ->
            val records = list.mapIndexed { index, day ->
                RecordCardModel(
                    index = index,
                    weather = WeatherMapper.mapCodeToIcon(day.weatherCode),
                    steps = day.steps,
                    date = day.date,
                )
            }
            RecordsModel(records)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecordsModel(emptyList())
        )
}
