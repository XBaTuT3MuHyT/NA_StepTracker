package com.example.na_steptracker

import android.app.Application
import com.example.na_steptracker.data.db.DataBase
import com.example.na_steptracker.data.steps.StepsDataSourceImpl
import com.example.na_steptracker.data.steps.StepsRepositoryImpl

class App: Application() {

    val database by lazy {
        DataBase.getInstance(this)
    }

    val stepsDao by lazy {
        database.stepsDao()
    }

    val stepsDataSource by lazy {
        StepsDataSourceImpl(stepsDao)
    }

    val stepsRepository by lazy {
        StepsRepositoryImpl(stepsDataSource)
    }

}
