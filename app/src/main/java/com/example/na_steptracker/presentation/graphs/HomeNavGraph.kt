package com.example.na_steptracker.presentation.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.na_steptracker.R
import com.example.na_steptracker.presentation.screens.home.home.HomeScreen
import com.example.na_steptracker.presentation.screens.home.settings.SettingsScreen
import com.example.na_steptracker.presentation.screens.home.stat.StatScreen
import androidx.annotation.StringRes

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.HOME,
        startDestination = "homeScreen",
    ) {
        composable(BottomBarScreen.Home.route) { HomeScreen() }
        composable(BottomBarScreen.Stat.route) { StatScreen() }
        composable(BottomBarScreen.Settings.route) { SettingsScreen(navController) }
    }
}

sealed class BottomBarScreen(
    val route: String,
    @param:StringRes val label: Int,
    val icon: ImageVector
) {
    object Home : BottomBarScreen("homeScreen", R.string.bottom_bar_today, Icons.Default.Home)
    object Stat :
        BottomBarScreen("statScreen", R.string.bottom_bar_statistics, Icons.Default.DateRange)

    object Settings :
        BottomBarScreen("settingsScreen", R.string.bottom_bar_more, Icons.Default.Settings)
}