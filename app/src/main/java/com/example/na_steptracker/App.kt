package com.example.na_steptracker

import android.app.Application
import com.example.na_steptracker.data.db.DataBase
import com.example.na_steptracker.data.prefs.stepsPrefs.PrefsDataSourceImpl
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefsImpl
import com.example.na_steptracker.data.steps.daily.StepsDataSourceImpl
import com.example.na_steptracker.data.steps.StepsRepositoryImpl
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDataSourceImpl

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

    val prefs by lazy {
        StepsPrefsImpl(applicationContext)
    }

    val prefsDataSource by lazy {
        PrefsDataSourceImpl(prefs)
    }

    val stepsRepository by lazy {
        StepsRepositoryImpl(stepsDataSource, hourlyStepsDataSource, prefsDataSource)
    }

}
