package com.example.na_steptracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.na_steptracker.data.steps.daily.Day
import com.example.na_steptracker.data.steps.daily.StepsDao
import com.example.na_steptracker.data.steps.hourly.HourlySteps
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDao

@TypeConverters(LocalDateConverter::class)
@Database(
    entities = [
        Day::class,
        HourlySteps::class
    ], version = 2
)
abstract class DataBase : RoomDatabase() {

    abstract fun stepsDao(): StepsDao
    abstract fun hourlyStepsDao(): HourlyStepsDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "database",
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
