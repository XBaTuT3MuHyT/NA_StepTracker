package com.example.na_steptracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.na_steptracker.data.auth.User
import com.example.na_steptracker.data.auth.UserDao
import com.example.na_steptracker.data.steps.daily.Day
import com.example.na_steptracker.data.steps.daily.StepsDao
import com.example.na_steptracker.data.steps.hourly.HourlySteps
import com.example.na_steptracker.data.steps.hourly.HourlyStepsDao
import com.example.na_steptracker.data.weather.local.WeatherDao
import com.example.na_steptracker.data.weather.local.WeatherEntity

@TypeConverters(LocalDateConverter::class)
@Database(
    entities = [
        Day::class,
        HourlySteps::class,
        WeatherEntity::class,
        User::class
    ], version = 6
)
abstract class DataBase : RoomDatabase() {

    abstract fun stepsDao(): StepsDao
    abstract fun hourlyStepsDao(): HourlyStepsDao
    abstract fun weatherDao(): WeatherDao
    abstract fun userDao(): UserDao

    companion object {
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `users` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `name` TEXT NOT NULL, 
                        `email` TEXT NOT NULL, 
                        `passwordHash` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 1. Создаем временную таблицу (сверяем всё: типы, CASCADE, NOT NULL)
                db.execSQL("""
            CREATE TABLE `days_new` (
                `date` TEXT NOT NULL, 
                `ownerId` INTEGER NOT NULL, 
                `steps` INTEGER NOT NULL, 
                PRIMARY KEY(`date`), 
                FOREIGN KEY(`ownerId`) REFERENCES `users`(`id`) ON UPDATE CASCADE ON DELETE CASCADE
            )
        """.trimIndent())

                // 2. Создаем индекс сразу на новую таблицу
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_days_ownerId` ON `days_new` (`ownerId`)")

                // 3. Копируем данные.
                // ВАЖНО: Если date в старой таблице был INTEGER, используй CAST(date AS TEXT)
                db.execSQL("""
            INSERT INTO `days_new` (date, ownerId, steps) 
            SELECT date, 0, steps FROM `days`
        """.trimIndent())

                // 4. Удаляем старье и переименовываем
                db.execSQL("DROP TABLE `days`")
                db.execSQL("ALTER TABLE `days_new` RENAME TO `days`")
            }
        }
    }
}
