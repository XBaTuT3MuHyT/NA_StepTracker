package com.example.na_steptracker.data.steps

import android.icu.util.LocaleData
import androidx.compose.ui.text.intl.Locale
import com.example.na_steptracker.domain.model.DaySteps
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StepsRepository {
    fun observeStepsForDate(
        date: LocalDate
    ): Flow<DaySteps>
    fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<DaySteps>>
}