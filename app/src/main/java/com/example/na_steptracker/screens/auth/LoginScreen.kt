package com.example.na_steptracker.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.na_steptracker.graphs.Graph

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("LogIn screen")
        Spacer(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Graph.HOME)
            }) {
            Text("LogIn")
        }
        ForgotPasswordClickableText()
        SignupClickableText()
    }
}

@Composable
fun LoginClickableText(){
    Text(
        text = "Есть аккаунт",
        fontSize = 16.sp,
        color = Color(0xFF1E88E5),
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {

        }
    )
}
@Composable
fun ForgotPasswordClickableText(){
    Text(
        text = "Забыли пароль?",
        fontSize = 16.sp,
        color = Color(0xFF1E88E5),
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
        }
    )
}
@Composable
fun SignupClickableText(){
    Text(
        text = "Регистрация",
        fontSize = 16.sp,
        color = Color(0xFF1E88E5),
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//    NA_StepTrackerTheme {
//        LoginScreen()
//    }
//}