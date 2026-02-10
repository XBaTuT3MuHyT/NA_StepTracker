package com.example.na_steptracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.na_steptracker.service.StepsForegroundService.StepsForegroundService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        getPermissions()
        startStepsService()

        setContent {
            MyApp()
        }
    }

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    1001
                )
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1002
                )
            }
        }
    }

    private fun startStepsService() {

        val intent = Intent(this, StepsForegroundService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}
