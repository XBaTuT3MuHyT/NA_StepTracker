package com.example.na_steptracker.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.na_steptracker.screens.home.HomeScreen
import com.example.na_steptracker.screens.home.SettingsScreen
import com.example.na_steptracker.screens.home.StatScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.HOME,
        startDestination = "homeScreen",
    ){
        composable(BottomBarScreen.Home.route){ HomeScreen(navController) }
        composable(BottomBarScreen.Stat.route){ StatScreen(navController) }
        composable(BottomBarScreen.Settings.route) { SettingsScreen(navController) }
    }
}

sealed class BottomBarScreen(
    val route: String,
    val label: String,
    val icon: ImageVector
){
    object Home: BottomBarScreen("homeScreen","Сегодня", Icons.Default.Home)
    object Stat: BottomBarScreen("statScreen","Отчет", Icons.Default.DateRange)
    object Settings: BottomBarScreen("settingsScreen","Еще", Icons.Default.Settings)
}