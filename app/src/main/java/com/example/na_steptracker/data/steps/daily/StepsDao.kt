package com.example.na_steptracker.data.steps.daily

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface StepsDao {

    @Query("SELECT * FROM days WHERE date = :date AND ownerId = :ownerId")
    fun observeStepsForDate(
        date: LocalDate,
        ownerId: Int,
    ): Flow<Day?>

    @Query("SELECT * FROM days WHERE ownerId = :ownerId AND date BETWEEN :from AND :to")
    fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate,
        ownerId: Int,
    ): Flow<List<Day>>

    @Transaction
    @Query("SELECT * FROM days WHERE ownerId = :ownerId ORDER BY steps DESC LIMIT 15")
    fun observeRecordDaysWithWeather(ownerId: Int): Flow<List<DayWithWeatherEntity>>

    @Query("SELECT * FROM days WHERE ownerId = :ownerId ORDER BY steps DESC LIMIT 15")
    fun observeRecordSteps(ownerId: Int): Flow<List<Day>>

    @Query("SELECT * FROM days WHERE ownerId = :ownerId")
    fun observeAllSteps(ownerId: Int): Flow<List<Day>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(day: Day)

    @Delete
    suspend fun deleteSteps(day: Day)
}