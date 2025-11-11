package com.example.na_steptracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NA_StepTrackerTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {BottomBar(navController)}
        ){innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = "homeScreen"
            ){
                composable(BottomBarScreen.Home.route){HomeScreen(navController)}
                composable(BottomBarScreen.Stat.route){StatScreen(navController)}
                composable(BottomBarScreen.Settings.route) {SettingsScreen(navController)}
        }
    }


    }
}

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector
){
    object Home: BottomBarScreen("homeScreen", Icons.Default.Home)
    object Stat: BottomBarScreen("statScreen", Icons.Default.DateRange)
    object Settings: BottomBarScreen("settingsScreen", Icons.Default.Settings)
}

