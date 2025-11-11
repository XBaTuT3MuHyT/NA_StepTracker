package com.example.na_steptracker

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.selects.select

@Composable
fun BottomBar(navController: NavController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Stat,
        BottomBarScreen.Settings
    )
    NavigationBar(
        modifier = Modifier.wrapContentHeight()
    ){
        screens.forEach {screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null)},
                selected = true,
                onClick = {navController.navigate(screen.route)}
            )
        }
    }
}