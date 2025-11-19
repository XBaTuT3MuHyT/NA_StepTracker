package com.example.na_steptracker.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.na_steptracker.R
import com.example.na_steptracker.graphs.Graph

@Composable
fun SettingsScreen(navController: NavController) {
    SettingsList(List<Setting>(20) { Setting("Setting ") }, navController)
}

@Composable
fun SettingsList(items: List<Setting>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(0.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ProfileSetting(navController)
        }
        item {
            Card(
                modifier = Modifier
                    .height(72.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.settings_steps_goal),
                    )
                    SettingsDropDown()
                }
            }
        }
        items(items) { item ->
            Card(
                modifier = Modifier
                    .height(52.dp)
                    .fillMaxWidth(),
                onClick = {},
                shape = RoundedCornerShape(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.label,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileSetting(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .height(150.dp),
        onClick = {
            showDialog = true
        },
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(2F)
            ) {
                Text(
                    text = stringResource(R.string.settings_profile_title),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Text(
                            text = "5632",
                            modifier = Modifier.padding(4.dp),
                            fontSize = 24.sp
                        )
                        Text(
                            text = stringResource(R.string.settings_profile_steps),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "56",
                            modifier = Modifier.padding(4.dp),
                            fontSize = 24.sp
                        )
                        Text(
                            text = stringResource(R.string.settings_profile_ccal),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1F),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Имя Фамилия",
                        modifier = Modifier.padding(2.dp, 0.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
    if (showDialog) {
        ProfileDialog(navController) { showDialog = false }
    }
}


@Composable
fun SettingsDropDown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("6000") }

    Box(
        modifier = Modifier.wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    expanded = true
                },
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(selectedItem)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("5000", "6000", "7000", "8000").forEach { goal ->
                DropdownMenuItem(
                    text = { Text(goal) },
                    onClick = {
                        selectedItem = goal
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileDialog(navController: NavController, onDismiss: () -> Unit) {

    var name by remember { mutableStateOf("имя") }
    var surname by remember { mutableStateOf("фамилия") }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.settings_profile_dialog_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { newName ->
                        name = newName
                    },
                    label = { Text(stringResource(R.string.settings_profile_dialog_name)) },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = surname,
                    onValueChange = { newSurname ->
                        surname = newSurname
                    },
                    label = { Text(stringResource(R.string.settings_profile_dialog_surname)) },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        navController.navigate(Graph.AUTH) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.settings_profile_dialog_button_exit_profile))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.settings_profile_dialog_button_close))
                }
            }
        }
    }
}

data class Setting(val label: String)

//@Preview(showBackground = true)
//@Composable
//fun DropDownPreview() {
//    NA_StepTrackerTheme {
//        SettingsDropDown()
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun DialogPreview(navController: NavController) {
//    NA_StepTrackerTheme {
//        ProfileDialog { navController }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun SettingsListPreview() {
//    SettingsList(List<Setting>(15) { Setting("Setting ") })
//}
