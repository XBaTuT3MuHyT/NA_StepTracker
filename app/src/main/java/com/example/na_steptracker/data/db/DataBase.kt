package com.example.na_steptracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(LocalDateConverter::class)
@Database(entities = [Day::class], version = 1)
abstract class DataBase: RoomDatabase() {

    abstract fun stepsDao(): StepsDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
