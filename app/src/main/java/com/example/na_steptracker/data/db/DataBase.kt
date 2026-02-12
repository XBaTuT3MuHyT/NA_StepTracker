package com.example.na_steptracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.na_steptracker.data.auth.User
import com.example.na_steptracker.data.auth.UserDao
import com.example.na_steptracker.data.steps.daily.Day
import com.example.na_steptracker.data.steps.daily.StepsDao
import com.example.na_steptracker.data.steps.hourly.HourlySteps
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDao
import com.example.na_steptracker.data.weather.local.WeatherDao
import com.example.na_steptracker.data.weather.local.WeatherEntity

@TypeConverters(LocalDateConverter::class)
@Database(
    entities = [
        Day::class,
        HourlySteps::class,
        WeatherEntity::class,
        User::class
    ], version = 5
)
abstract class DataBase : RoomDatabase() {

    abstract fun stepsDao(): StepsDao
    abstract fun hourlyStepsDao(): HourlyStepsDao
    abstract fun weatherDao(): WeatherDao
    abstract fun userDao(): UserDao

    companion object {
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `users` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `name` TEXT NOT NULL, 
                        `email` TEXT NOT NULL, 
                        `passwordHash` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
