package com.example.na_steptracker.data.steps.hourly

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HourlyStepsDao {

    @Query("SELECT * FROM hourly_steps")
    fun observeHourlySteps() : Flow<List<HourlySteps>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStepsForHour(hourlySteps: HourlySteps)

    @Query("DELETE FROM hourly_steps")
    suspend fun deleteAll()
}