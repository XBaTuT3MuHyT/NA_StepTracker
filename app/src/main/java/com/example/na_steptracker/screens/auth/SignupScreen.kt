package com.example.na_steptracker.screens.auth

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.na_steptracker.R
import com.example.na_steptracker.graphs.Graph
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme

@Composable
fun SignupScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
//    var surName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var repeatPasswordVisible by remember { mutableStateOf(false) }


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
            text = stringResource(R.string.signup_title), style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(36.dp))

        Column(
            modifier = Modifier.widthIn(max = 420.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),

                value = name,
                onValueChange = { newName ->
                    name = newName
                },
                label = { Text(stringResource(R.string.signup_textfield_name)) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
            )
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//
//                value = surName,
//                onValueChange = { newSurName ->
//                    surName = newSurName
//                },
//                label = { Text(stringResource(R.string.signup_textfield_surname)) },
//                shape = RoundedCornerShape(16.dp),
//                singleLine = true,
//            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),

                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                },
                label = { Text(stringResource(R.string.login_textfield_email)) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                },
                label = { Text(stringResource(R.string.login_textfield_password)) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    }
                    val description = if (passwordVisible) "Скрыть пароль" else "Показать пароль"
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }) {
                        Icon(image, description)
                    }
                })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = repeatPassword,
                onValueChange = { newPassword ->
                    repeatPassword = newPassword
                },
                label = { Text(stringResource(R.string.signup_textfield_repeat_password)) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                visualTransformation = if (repeatPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (repeatPasswordVisible) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    }
                    val description = if (repeatPasswordVisible) "Скрыть пароль" else "Показать пароль"
                    IconButton(
                        onClick = { repeatPasswordVisible = !repeatPasswordVisible }) {
                        Icon(image, description)
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
                modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(Graph.HOME) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }) {
                Text(stringResource(R.string.signup_button_signup))
            }
            Text("Или продолжить с помощью")
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                onClick = {}) {
                Text(stringResource(R.string.login_button_google))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.widthIn(max = 420.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.signup_text_account_exist))
            LoginClickableText(navController)
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