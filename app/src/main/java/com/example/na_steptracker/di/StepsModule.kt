package com.example.na_steptracker.di

import com.example.na_steptracker.data.steps.StepsRepositoryImpl
import com.example.na_steptracker.data.steps.daily.StepsDataSource
import com.example.na_steptracker.data.steps.daily.StepsDataSourceImpl
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDataSource
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDataSourceImpl
import com.example.na_steptracker.domain.StepsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StepsModule {

    @Binds
    @Singleton
    abstract fun bindStepsDataSource(impl: StepsDataSourceImpl): StepsDataSource

    @Binds
    @Singleton
    abstract fun bindHourlyStepsDataSource(impl: HourlyStepsDataSourceImpl): HourlyStepsDataSource

    @Binds
    @Singleton
    abstract fun bindStepsRepository(impl: StepsRepositoryImpl): StepsRepository
}
