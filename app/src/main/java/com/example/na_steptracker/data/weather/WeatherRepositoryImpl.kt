package com.example.na_steptracker.data.weather

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.na_steptracker.data.weather.local.LocalWeatherDataSource
import com.example.na_steptracker.data.weather.local.WeatherEntity
import com.example.na_steptracker.data.weather.remote.WeatherDataSource
import com.example.na_steptracker.domain.WeatherRepository
import com.example.na_steptracker.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherDataSource,
    private val localSource: LocalWeatherDataSource
) : WeatherRepository {

    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double
    ): Weather {
           val response = remoteSource.getCurrentWeather(lat, lon)
        Log.d("retrofit", "${response.current}")
        return Weather(
            temp = response.current.temperature,
            weatherCode = response.current.code,
        )
    }

    override suspend fun fetchYesterdayWeather(
        lat: Double,
        lon: Double
    ): Int {
        val response = remoteSource.getYesterdayWeather(lat, lon)
        return response.daily.code[0]
    }

    override fun observeWeatherForDate(date: LocalDate): Flow<Weather?> =
        localSource.observeWeatherForDate(date).map {
            Weather(
                weatherCode = it?.weatherCode ?: 0,
                temp = 0.0,
            )
        }

    override suspend fun insertWeather(weatherCode: Int, date: LocalDate) {
        localSource.insertWeather(
            WeatherEntity(
                weatherCode = weatherCode,
                date = date,
            )
        )
    }

}
