package com.example.na_steptracker.data.auth

import com.example.na_steptracker.data.prefs.settingsPrefs.SettingsPrefs
import com.example.na_steptracker.domain.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val prefs: SettingsPrefs,
): AuthRepository {

    override val isLoggedIn: Flow<Boolean> = prefs.isLoggedIn

    override suspend fun changeLoggedInState(isLogged: Boolean) {
        prefs.saveIsLoggedIn(isLogged)
    }

    override val currentUserId: Flow<Int?> = prefs.currentUserId

    override suspend fun registerUser(user: User) {
        userDao.registerUser(user)
        prefs.saveIsLoggedIn(true)
        prefs.saveCurrentUserId(user.id)
    }

    override suspend fun login(
        email: String,
        passwordHash: String
    ): Boolean {
        val user = userDao.loginUser(email, passwordHash)
        if (user != null) {
            prefs.saveIsLoggedIn(true)
            prefs.saveCurrentUserId(user.id)
            return true
        }
        return false
    }

}