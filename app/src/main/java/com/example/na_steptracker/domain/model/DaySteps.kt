package com.example.na_steptracker.domain.model

import java.time.LocalDate

data class DaySteps(
    val date: LocalDate,
    val steps: Int,
)
