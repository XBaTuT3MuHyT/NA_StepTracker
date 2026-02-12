package com.example.na_steptracker.screens.mane

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.na_steptracker.components.BottomBar
import com.example.na_steptracker.components.TopBar
import com.example.na_steptracker.graphs.BottomBarScreen
import com.example.na_steptracker.graphs.Graph
import com.example.na_steptracker.graphs.RootNavGraph
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme

@Composable
fun MyApp() {
    val viewModel: MainViewModel = hiltViewModel()
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

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

            when (isLoggedIn) {
                null -> {
                    CircularProgressIndicator()
                }
                else -> {
                    val startDestination = remember {
                        if (isLoggedIn == true) Graph.HOME else Graph.AUTH
                    }

                    Log.d("startDestination", "$startDestination, ${isLoggedIn}")
                    RootNavGraph(
                        navController = navController,
                        innerPadding = innerPadding,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
