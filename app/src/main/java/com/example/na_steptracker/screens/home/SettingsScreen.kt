package com.example.na_steptracker.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    Text("Settings Screen")
    SettingsList(List<Setting>(20){Setting("Setting ")})
}
@Composable
fun SettingsList(items: List<Setting>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 36.dp),
        contentPadding = PaddingValues(0.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            Card(
                modifier = Modifier
                    .height(52.dp),
                onClick = {},
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = item.label,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)

                )
            }

        }
    }
}
data class Setting(val label: String)

@Preview(
    showBackground = true,

    )
@Composable
fun SettingsListPreview(){
    SettingsList(List<Setting>(15){Setting("Setting ")})
}