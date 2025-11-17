package com.example.na_steptracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.na_steptracker.graphs.BottomBarScreen
import com.example.na_steptracker.graphs.RootNavGraph
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme

@Composable
fun MyApp() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarScreens = listOf(
        BottomBarScreen.Home.route,
        BottomBarScreen.Settings.route,
        BottomBarScreen.Stat.route
    )

    NA_StepTrackerTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (currentRoute in bottomBarScreens) {
                    TopBar(navController)
                }
            },
            bottomBar = {
                if (currentRoute in bottomBarScreens) {
                    BottomBar(navController)
                }
            }
        ) { innerPadding ->
            RootNavGraph(navController, innerPadding)
        }


    }
}