package com.example.na_steptracker.data.weather.local

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalWeatherDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : LocalWeatherDataSource {
    override fun observeWeatherForDate(date: LocalDate): Flow<WeatherEntity?> =
        weatherDao.observeWeatherForDate(date)

    override suspend fun insertWeather(weatherEntity: WeatherEntity) =
        weatherDao.insertWeather(weatherEntity)
}