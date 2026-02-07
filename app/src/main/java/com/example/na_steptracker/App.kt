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
import com.example.na_steptracker.data.weather.remote.RetrofitInstance
import com.example.na_steptracker.data.weather.remote.WeatherDataSourceImpl
import com.example.na_steptracker.data.weather.WeatherRepositoryImpl
import com.example.na_steptracker.data.weather.local.LocalWeatherDataSourceImpl
import kotlin.getValue

class App: Application() {

    val database by lazy {
        DataBase.getInstance(this)
    }

    val stepsDao by lazy {
        database.stepsDao()
    }

    val hourlyStepsDao by lazy {
        database.hourlyStepsDao()
    }

    val stepsDataSource by lazy {
        StepsDataSourceImpl(stepsDao)
    }

    val hourlyStepsDataSource by lazy {
        HourlyStepsDataSourceImpl(hourlyStepsDao)
    }

    private val settingsPrefsImpl by lazy { SettingsPrefsImpl(this) }

    private val stepsPrefsImpl by lazy { StepsPrefsImpl(this) }

    val stepDisplayPrefs: StepsDisplayPrefs get() = stepsPrefsImpl

    val stepCounterStorage: StepsPrefs get() = stepsPrefsImpl

    val stepsRepository by lazy {
        StepsRepositoryImpl(stepsDataSource, hourlyStepsDataSource, stepDisplayPrefs, settingsPrefsImpl)
    }

    val weatherApi by lazy {
        RetrofitInstance.weatherApiService
    }

    val weatherRemoteDataSource by lazy {
        WeatherDataSourceImpl(weatherApi)
    }

    val weatherDao by lazy {
        database.weatherDao()
    }

    val weatherLocaleDataSource by lazy {
        LocalWeatherDataSourceImpl(weatherDao)
    }

    val weatherRepository by lazy {
        WeatherRepositoryImpl(
            weatherRemoteDataSource,
            localSource = weatherLocaleDataSource
        )
    }

}
