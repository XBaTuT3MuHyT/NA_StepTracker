package com.example.na_steptracker.presentation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.na_steptracker.presentation.screens.auth.ForgotPasswordScreen
import com.example.na_steptracker.presentation.screens.auth.login.LoginScreen
import com.example.na_steptracker.presentation.screens.auth.signup.SignupScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = Graph.AUTH,
        startDestination = "login"
    ){
        composable("login") { LoginScreen(navController) }
        composable("forgot") { ForgotPasswordScreen(navController) }
        composable("signup") { SignupScreen(navController) }
    }


}

