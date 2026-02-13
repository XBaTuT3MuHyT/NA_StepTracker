package com.example.na_steptracker.presentation.screens.home.stat

import com.example.na_steptracker.presentation.components.RecordCardModel

data class WeeklyChartModel(
    val target: Int,
    val days: List<ChartPoint>,
)

data class DailyChartModel(
    val hours: List<ChartPoint>,
)

data class RecordsModel(
    val records: List<RecordCardModel>
)


data class ChartPoint(
    val label: String?,
    val value: Int
)