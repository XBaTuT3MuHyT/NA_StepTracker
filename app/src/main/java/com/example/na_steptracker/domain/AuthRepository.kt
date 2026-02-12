package com.example.na_steptracker.domain

import com.example.na_steptracker.data.auth.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isLoggedIn: Flow<Boolean>
    suspend fun changeLoggedInState(isLogged: Boolean)
    suspend fun registerUser(user: User)
    suspend fun login(email: String, passwordHash: String): Boolean
}