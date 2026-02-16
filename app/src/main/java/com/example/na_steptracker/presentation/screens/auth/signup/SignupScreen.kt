package com.example.na_steptracker.presentation.screens.auth.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.na_steptracker.R
import com.example.na_steptracker.presentation.graphs.Graph
import com.example.na_steptracker.presentation.screens.auth.login.ClickableText
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme

@Composable
fun SignupScreen(navController: NavController) {

    val viewModel: SignupViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                SignupSideEffect.NavigateToForgotPassword -> {
                    navController.navigate("forgot") {
                        launchSingleTop = true
                        popUpTo("signup")
                    }
                }

                SignupSideEffect.NavigateToHome -> {
                    navController.navigate(Graph.HOME) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }

                SignupSideEffect.NavigateToLogin -> {
                    navController.navigate("login") {
                        launchSingleTop = true
                        popUpTo("signup")
                    }
                }

                is SignupSideEffect.ShowToast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Content(
        state = state,
        onNameChanged = { viewModel.onEvent(SignUpUiEvent.OnNameChanged(it)) },
        onEmailChanged = { viewModel.onEvent(SignUpUiEvent.OnEmailChanged(it)) },
        onPasswordChanged = { viewModel.onEvent(SignUpUiEvent.OnPasswordChanged(it)) },
        onRepeatPasswordChanged = { viewModel.onEvent(SignUpUiEvent.OnRepeatPasswordChanged(it)) },
        onVisibilityChange = { viewModel.onEvent(SignUpUiEvent.OnVisibilityChange) },
        onVisibilityRepeatPasswordChange = { viewModel.onEvent(SignUpUiEvent.OnVisibilityRepeatPasswordChange) },
        onSignUpClick = { viewModel.onEvent(SignUpUiEvent.OnSignUpClick) },
        onGoogleLoginClick = { viewModel.onEvent(SignUpUiEvent.OnGoogleSignUpClick) },
        onLoginClick = { viewModel.onEvent(SignUpUiEvent.OnLoginClick) },
    )
}

@Composable
fun Content(
    state: SignupUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatPasswordChanged: (String) -> Unit,
    onVisibilityChange: () -> Unit,
    onVisibilityRepeatPasswordChange: () -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.signup_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(36.dp))

        Column(
            modifier = Modifier.widthIn(max = 420.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),

                value = state.name,
                onValueChange = onNameChanged,
                label = { Text(stringResource(R.string.signup_textfield_name)) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),

                value = state.email,
                onValueChange = onEmailChanged,
                label = { Text(stringResource(R.string.login_textfield_email)) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.password,
                onValueChange = onPasswordChanged,
                label = { Text(stringResource(R.string.login_textfield_password)) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                visualTransformation = if (state.isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (state.isPasswordVisible) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    }
                    IconButton(
                        onClick = onVisibilityChange
                    ) {
                        Icon(image, null)
                    }
                })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.repeatPassword,
                onValueChange = onRepeatPasswordChanged,
                label = { Text(stringResource(R.string.signup_textfield_repeat_password)) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                visualTransformation = if (state.isRepeatPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (state.isRepeatPasswordVisible) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    }
                    IconButton(
                        onClick = onVisibilityRepeatPasswordChange
                    ) {
                        Icon(image, null)
                    }
                })
        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.widthIn(max = 420.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSignUpClick,
            ) {
                Text(stringResource(R.string.signup_button_signup))
            }
            Text("Или продолжить с помощью")
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                onClick = onGoogleLoginClick
            ) {
                Text(stringResource(R.string.login_button_google))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.widthIn(max = 420.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.signup_text_account_exist))
            ClickableText(
                text = stringResource(R.string.clickabletext_account_exist),
                onClick = onLoginClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    NA_StepTrackerTheme {
        val navController = rememberNavController()
        SignupScreen(navController)
    }
}