package com.example.na_steptracker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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

    @Query("SELECT * FROM days")
    fun observeAllSteps(): Flow<List<Day>>

    @Insert
    suspend fun insertSteps(day: Day)

    @Delete
    suspend fun deleteSteps(day: Day)
}
