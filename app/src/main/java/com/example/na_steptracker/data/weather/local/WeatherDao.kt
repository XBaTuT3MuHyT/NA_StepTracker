package com.example.na_steptracker.data.weather.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE date = :date")
    fun observeWeatherForDate(date: LocalDate): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherEntity: WeatherEntity)
}