package com.example.na_steptracker.di

import com.example.na_steptracker.data.weather.WeatherRepositoryImpl
import com.example.na_steptracker.data.weather.local.LocalWeatherDataSource
import com.example.na_steptracker.data.weather.local.LocalWeatherDataSourceImpl
import com.example.na_steptracker.data.weather.remote.WeatherApiService
import com.example.na_steptracker.data.weather.remote.WeatherDataSource
import com.example.na_steptracker.data.weather.remote.WeatherDataSourceImpl
import com.example.na_steptracker.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherModule {

    @Binds
    @Singleton
    abstract fun bindWeatherDataSource(impl: WeatherDataSourceImpl): WeatherDataSource

    @Binds
    @Singleton
    abstract fun bindLocalWeatherDataSource(impl: LocalWeatherDataSourceImpl): LocalWeatherDataSource

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {
        @Provides
        @Singleton
        fun provideWeatherApiService(
            retrofit: Retrofit
        ): WeatherApiService =
            retrofit.create(WeatherApiService::class.java)
    }
}
