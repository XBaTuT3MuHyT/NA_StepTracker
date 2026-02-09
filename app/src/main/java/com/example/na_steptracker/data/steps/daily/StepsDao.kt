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

    @Query("SELECT * FROM days WHERE date = :date")
    fun observeStepsForDate(date: LocalDate): Flow<Day?>

    @Query("SELECT * FROM days WHERE date BETWEEN :from AND :to")
    fun observeStepsForPeriod(
        from: LocalDate,
        to: LocalDate
    ): Flow<List<Day>>

    @Transaction
    @Query("SELECT * FROM days ORDER BY steps DESC LIMIT 15")
    fun observeRecordDaysWithWeather(): Flow<List<DayWithWeather>>

    @Query("SELECT * FROM days ORDER BY steps DESC LIMIT 15")
    fun observeRecordSteps(): Flow<List<Day>>

    @Query("SELECT * FROM days")
    fun observeAllSteps(): Flow<List<Day>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(day: Day)

    @Delete
    suspend fun deleteSteps(day: Day)
}