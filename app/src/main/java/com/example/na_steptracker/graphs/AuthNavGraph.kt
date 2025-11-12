package com.example.na_steptracker.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.na_steptracker.screens.auth.ForgotPasswordScreen
import com.example.na_steptracker.screens.auth.LoginScreen
import com.example.na_steptracker.screens.auth.SignupScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination = "login"
    ){
        composable("login") { LoginScreen(navController) }
        composable("forgot") { ForgotPasswordScreen(navController) }
        composable("signup") { SignupScreen(navController) }
    }


}

