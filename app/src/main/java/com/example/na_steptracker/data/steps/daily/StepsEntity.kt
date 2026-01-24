package com.example.na_steptracker.data.steps.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "days")
data class Day(
    @PrimaryKey val date: LocalDate,
    val steps: Int,
)