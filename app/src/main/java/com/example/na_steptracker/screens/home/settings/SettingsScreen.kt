package com.example.na_steptracker.screens.home.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.na_steptracker.App
import com.example.na_steptracker.R
import com.example.na_steptracker.domain.model.AppLanguage
import com.example.na_steptracker.graphs.Graph

@Composable
fun SettingsScreen(navController: NavController) {
    val app = LocalContext.current.applicationContext as App
    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(app.stepsRepository, navController)
    )
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingsSideEffect.NavigateToAuth -> {
                    navController.navigate(Graph.AUTH) {
                        popUpTo(0)
                    }
                }
            }
        }
    }
    Content(
        items = SettingsProvider.provide(
            state = state,
            onEvent = { viewModel.onEvent(it) }
        ),
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun Content(
    items: List<SettingSample>,
    state: SettingsUiState,
    onEvent: (SettingsUiEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ProfileSetting(
                profileModel = state.profile,
                onExit = { onEvent(SettingsUiEvent.OnClickExit) },
                onNameValueChanged = { onEvent(SettingsUiEvent.OnNameValueChanged(it)) },
                onSurnameValueChanged = { onEvent(SettingsUiEvent.OnSurnameValueChanged(it)) }
            )
        }

        items(items) { item ->
            Setting(
                settingSample = item,
            )
        }
    }
}

data class SettingSample(
    val titleRes: Int,
    val icon: ImageVector? = null,
    val dropDown: (@Composable () -> Unit)? = null,
)

object SettingsProvider {

    fun provide(
        state: SettingsUiState,
        onEvent: (SettingsUiEvent) -> Unit
    ): List<SettingSample> = listOf(
        SettingSample(
            titleRes = R.string.settings_steps_goal,
            icon = Icons.Default.MyLocation,
            dropDown = {
                SettingsDropDown(
                    items = (2000..15000 step 500).map { it.toString() },
                    selectedItem = state.settings.selectedSteps.toString(),
                    onItemSelected = { onEvent(SettingsUiEvent.OnGoalSelected(it.toInt())) },
                )
            }
        ),
        SettingSample(
            titleRes = R.string.settings_select_language,
            icon = Icons.Default.Language,
            dropDown = {
                SettingsDropDown(
                    items = AppLanguage.entries.map { it.displayName },
                    selectedItem = state.settings.selectedLanguage.displayName,
                    onItemSelected = { selectedName ->
                        val language = AppLanguage.fromDisplayName(selectedName)
                        language?.let { onEvent(SettingsUiEvent.OnLanguageSelected(it)) }
                    }
                )
            }
        ),
        SettingSample(
            titleRes = R.string.settings_permision,
            icon = Icons.Default.LockOpen,
        ),
    )
}

@Composable
fun Setting(
    settingSample: SettingSample
) {
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
        ) {
            settingSample.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                text = stringResource(settingSample.titleRes),
            )
            Spacer(modifier = Modifier.weight(1f))
            settingSample.dropDown?.let {
                it()
            }

        }
    }
}

@Composable
fun ProfileSetting(
    profileModel: ProfileModel,
    onExit: () -> Unit,
    onNameValueChanged: (String) -> Unit,
    onSurnameValueChanged: (String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .height(160.dp),
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
                modifier = Modifier.weight(1.5F)
            ) {
                Text(
                    text = stringResource(R.string.settings_profile_title),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(start = 26.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceAround
                ) {
                    Column {
                        Text(
                            text = "${profileModel.steps}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.alpha(0.5f),
                            text = stringResource(R.string.settings_profile_steps),
                        )
                    }
                    Column {
                        Text(
                            text = "56",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.alpha(0.5f),
                            text = stringResource(R.string.settings_profile_ccal),
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
                Text(
                    text = "${profileModel.name} ${profileModel.surname}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    if (showDialog) {
        ProfileDialog(
            profileModel = profileModel,
            onDismiss = { showDialog = false },
            onExit = { onExit() },
            onNameValueChanged = { onNameValueChanged(it) },
            onSurnameValueChanged = { onSurnameValueChanged(it) },
        )
    }
}

@Composable
fun SettingsDropDown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

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
            Text(
                text = selectedItem,
                color = MaterialTheme.colorScheme.primary
            )
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            modifier = Modifier.heightIn(max = 300.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileDialog(
    profileModel: ProfileModel,
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    onNameValueChanged: (String) -> Unit,
    onSurnameValueChanged: (String) -> Unit,
) {

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
                    value = profileModel.name,
                    onValueChange = { onNameValueChanged(it) },
                    label = { Text(stringResource(R.string.settings_profile_dialog_name)) },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = profileModel.surname,
                    onValueChange = { onSurnameValueChanged(it) },
                    label = { Text(stringResource(R.string.settings_profile_dialog_surname)) },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onDismiss()
                        onExit()
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

//@Preview(showBackground = true)
//@Composable
//fun DropDownPreview() {
//    val navController = rememberNavController()
//    NA_StepTrackerTheme {
//        ProfileSetting(navController)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DialogPreview() {
//    NA_StepTrackerTheme {
//        val navController = rememberNavController()
//        ProfileDialog(navController) {}
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun SettingsListPreview() {
//    SettingsList(List<Setting>(15) { Setting("Setting ") })
//}
