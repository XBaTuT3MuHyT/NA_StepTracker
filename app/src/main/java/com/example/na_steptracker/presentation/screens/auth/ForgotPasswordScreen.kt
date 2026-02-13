package com.example.na_steptracker.presentation.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.na_steptracker.R
import com.example.na_steptracker.presentation.screens.auth.login.AuthSpacer
import com.example.na_steptracker.presentation.screens.auth.login.ClickableText

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Forgot password \nscreen"
        )
        AuthSpacer(325.dp)
        ClickableText(
            text = stringResource(R.string.clickabletext_account_exist),
            onClick = { navController.navigate("login") }
        )
        ClickableText(
            text = stringResource(R.string.clickabletext_signup),
            onClick = { navController.navigate("signup") }
        )
    }
}
