package com.example.na_steptracker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.na_steptracker.graphs.BottomBarScreen
import com.example.na_steptracker.graphs.Graph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title = when (currentRoute) {
        BottomBarScreen.Home.route -> stringResource(BottomBarScreen.Home.label)
        BottomBarScreen.Stat.route -> stringResource(BottomBarScreen.Stat.label)
        BottomBarScreen.Settings.route -> stringResource(BottomBarScreen.Settings.label)
        else -> {
            ""
        }
    }
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        })
}
