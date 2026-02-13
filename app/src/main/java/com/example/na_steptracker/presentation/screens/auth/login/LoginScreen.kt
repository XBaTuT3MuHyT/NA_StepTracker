package com.example.na_steptracker.presentation.screens.auth.login

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.na_steptracker.R
import com.example.na_steptracker.presentation.graphs.Graph
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {

                LoginSideEffect.NavigateToHome -> {
                    navController.navigate(Graph.HOME) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }

                }

                LoginSideEffect.NavigateToForgotPassword -> {
                    navController.navigate("forgot"){
                        launchSingleTop = true
                        popUpTo("login")
                    }

                }

                LoginSideEffect.NavigateToSignup -> {
                    navController.navigate("signup"){
                        launchSingleTop = true
                        popUpTo("login")
                    }
                }
            }
        }
    }

    Content(
        state = state,
        onEmailChanged = { viewModel.onEvent(LoginUiEvent.OnEmailChanged(it)) },
        onPasswordChanged = { viewModel.onEvent(LoginUiEvent.OnPasswordChanged(it)) },
        onLoginClick = { viewModel.onEvent(LoginUiEvent.OnLoginClick) },
        onSignupClick = { viewModel.onEvent(LoginUiEvent.OnSignupClick) },
        onForgotPasswordClick = { viewModel.onEvent(LoginUiEvent.OnForgotPasswordClick) },
        onGoogleLoginClick = {  },
        onVisibilityChange = { viewModel.onEvent(LoginUiEvent.OnVisibilityChange) }
    )

}


@Composable
fun Content(
    state: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onVisibilityChange: () -> Unit,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
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
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier.widthIn(max = 420.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ClickableText(
                    text = stringResource(R.string.clickabletext_forgot_password),
                    onClick = onForgotPasswordClick,
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.widthIn(max = 420.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                onClick = onLoginClick
            ) {
                Text(stringResource(R.string.login_button_login))
            }
            Text(stringResource(R.string.login_text_login_with))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
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
            Text(stringResource(R.string.login_text_no_account))
            ClickableText(
                text = stringResource(R.string.clickabletext_signup),
                onClick = onSignupClick
            )
        }
    }
}


@Composable
fun ClickableText(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable {onClick()},
    )
}

@Composable
fun AuthSpacer(
    height: Dp = 300.dp
) {
    Spacer(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    NA_StepTrackerTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}
