package com.example.na_steptracker.service.StepsForegroundService

import android.util.Log
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefs
import com.example.na_steptracker.di.ApplicationScope
import com.example.na_steptracker.domain.StepsRepository
import com.example.na_steptracker.domain.WeatherRepository
import com.example.na_steptracker.domain.model.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

data class StepsState(
    val lastSensor: Int?,
    val lastDate: LocalDate,
    val lastHour: Int,
    val daySteps: Int,
    val hourSteps: Int
)

class StepsCollector @Inject constructor(
    private val stepsRepository: StepsRepository,
    private val weatherRepository: WeatherRepository,
    private val stepsPrefs: StepsPrefs,
    @ApplicationScope private val scope: CoroutineScope
) {
    private var state: StateFlow<StepsState> = combine(
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
            saveDay(s, lastSensorValue = last, lastSavedDate = today)
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

    private fun saveDay(
        s: StepsState,
        lastSensorValue: Int,
        lastSavedDate: LocalDate
        ) {
        scope.launch {
            stepsRepository.saveDay(
                date = s.lastDate,
                steps = s.daySteps
            )

            stepsRepository.deleteAllHours()

            stepsPrefs.saveDay(
                lastSensorValue = lastSensorValue,
                lastSavedDate = lastSavedDate,
                currentDateSteps = 0
            )
            runCatching {
                val weatherCode = weatherRepository.fetchYesterdayWeather(59.53, 29.54)
                weatherRepository.insertWeather(
                    weatherCode = weatherCode,
                    date = s.lastDate
                )
            }
        }
    }
}
