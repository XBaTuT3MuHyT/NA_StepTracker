package com.example.na_steptracker.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme

@Composable
fun AuthScreen(
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LogIn screen",
            style = MaterialTheme.typography.titleLarge
            )
        Spacer(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
//                navController.navigate(Graph.HOME)
                }) {
                Text(
                    text = "LogIn",
                    style = MaterialTheme.typography.labelLarge
                )
            }
//            ForgotPasswordClickableText()
//            SignupClickableText()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    NA_StepTrackerTheme {
        AuthScreen()
    }
}
