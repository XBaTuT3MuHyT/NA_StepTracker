package com.example.na_steptracker.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Forgot password \nscreen"
        )
        Spacer(
            modifier = Modifier
                .height(28.dp)
                .fillMaxWidth()
        )
        LoginClickableText(navController)
        SignupClickableText(navController)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ForgotPasswordPreview(){
//    NA_StepTrackerTheme {
//        ForgotPasswordScreen()
//    }
//}