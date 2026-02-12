package com.example.na_steptracker.data.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash")
    suspend fun loginUser(email: String, passwordHash: String): User?


}