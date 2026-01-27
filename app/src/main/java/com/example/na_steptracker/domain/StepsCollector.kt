package com.example.na_steptracker.domain

import android.util.Log
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

data class StepsState(
    val lastSensor: Int?,
    val lastDate: LocalDate,
    val lastHour: Int,
    val daySteps: Int,
    val hourSteps: Int
)

class StepsCollector(
    private val stepsRepository: StepsRepository,
    private val stepsPrefs: StepsPrefs,
    private val scope: CoroutineScope
) {
    private var state: StateFlow<StepsState>

    init {
        state = combine(
            stepsPrefs.lastSensorValue,
            stepsPrefs.lastSavedDate,
            stepsPrefs.lastSavedHour,
            stepsPrefs.currentDateSteps,
            stepsPrefs.currentHourSteps,
        ) { sensor, date, hour, d, h ->
            StepsState(
                lastSensor = sensor,
                lastDate = date,
                lastHour = hour,
                daySteps = d,
                hourSteps = h
            )
        }.stateIn(
            scope,
            SharingStarted.Eagerly,
            StepsState(
                lastSensor = null,
                lastDate = LocalDate.now(),
                lastHour = LocalDateTime.now().hour,
                daySteps = 0,
                hourSteps = 0
            )
        )
    }

    fun onNewSensorValue(newValue: Int) {
        val s = state.value

        val now = LocalDateTime.now()
        val today = now.toLocalDate()
        val hour = now.hour

        val last = s.lastSensor ?: run {
            Log.d("StepsService", "ласт валью нал lastValue ${s.lastSensor} newValue $newValue")
            scope.launch {
                stepsPrefs.saveLastSensorValue(newValue)
            }
            return
        }

        if (newValue < last) return

        val delta = newValue - last

        if (hour != s.lastHour) {
            Log.d("StepsService", "новый час $hour шагов : ${s.hourSteps}")
            scope.launch {
                stepsRepository.saveHour(
                    hour = s.lastHour,
                    steps = s.hourSteps
                )

                stepsPrefs.saveHour(
                    newValue,
                    hour,
                    currentHourSteps = 0,
                )
            }
        }

        if (today != s.lastDate) {
            scope.launch {
                stepsRepository.saveDay(
                    date = s.lastDate,
                    steps = s.daySteps
                )

                stepsPrefs.saveDay(
                    lastSensorValue = newValue,
                    lastSavedDate = today,
                    currentDateSteps = 0
                )
            }
        }

        val currentHourSteps = s.hourSteps + delta
        val currentDateSteps = s.daySteps + delta

        Log.d(
            "StepsService",
            """тикБ было: lastSensorValue = ${s.lastSensor}
                lastSavedDate = ${s.lastDate}
                currentDateSteps = ${s.daySteps}
                lastSavedHour = ${s.lastHour}
                currentHourSteps = ${s.hourSteps}"""
        )

        scope.launch {
            stepsPrefs.saveSnapshot(
                lastSensorValue = newValue,
                currentDateSteps = currentDateSteps,
                currentHourSteps = currentHourSteps,
            )
        }
    }
}
