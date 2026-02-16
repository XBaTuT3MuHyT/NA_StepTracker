package com.example.na_steptracker.di

import android.content.Context
import androidx.room.Room
import com.example.na_steptracker.data.auth.UserDao
import com.example.na_steptracker.data.db.DataBase
import com.example.na_steptracker.data.db.DataBase.Companion.MIGRATION_6_7
import com.example.na_steptracker.data.steps.daily.StepsDao
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDao
import com.example.na_steptracker.data.weather.local.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            "database"
        )
            .addMigrations(DataBase.MIGRATION_4_5, DataBase.MIGRATION_5_6, MIGRATION_6_7)
            .build()
    }

    @Provides
    @Singleton
    fun provideStepsDao(
        database: DataBase
    ): StepsDao {
        return database.stepsDao()
    }

    @Provides
    @Singleton
    fun provideHourlyStepsDao(
        database: DataBase
    ): HourlyStepsDao {
        return database.hourlyStepsDao()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(
        database: DataBase
    ): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(
        database: DataBase
    ): UserDao {
        return database.userDao()
    }
}
