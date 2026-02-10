package com.example.na_steptracker

import android.app.Application
import com.example.na_steptracker.data.db.DataBase
import com.example.na_steptracker.data.prefs.settingsPrefs.SettingsPrefsImpl
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsDisplayPrefs
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefs
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefsImpl
import com.example.na_steptracker.data.steps.daily.StepsDataSourceImpl
import com.example.na_steptracker.data.steps.StepsRepositoryImpl
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDataSourceImpl
import com.example.na_steptracker.data.weather.remote.WeatherDataSourceImpl
import com.example.na_steptracker.data.weather.WeatherRepositoryImpl
import com.example.na_steptracker.data.weather.local.LocalWeatherDataSourceImpl
import dagger.hilt.android.HiltAndroidApp
import kotlin.getValue

@HiltAndroidApp
class App: Application()
