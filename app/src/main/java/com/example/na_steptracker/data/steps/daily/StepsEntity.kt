package com.example.na_steptracker.data.steps.daily

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.na_steptracker.data.auth.User
import java.time.LocalDate

@Entity(
    tableName = "days",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["ownerId"])]
)

data class Day(
    @PrimaryKey val date: LocalDate,
    val ownerId: Int,
    val steps: Int,
)