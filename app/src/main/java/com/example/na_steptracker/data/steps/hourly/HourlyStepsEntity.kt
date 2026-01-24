package com.example.na_steptracker.data.steps.hourly

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_steps")
data class HourlySteps (
    @PrimaryKey val hour: Int,
    val steps: Int,
)