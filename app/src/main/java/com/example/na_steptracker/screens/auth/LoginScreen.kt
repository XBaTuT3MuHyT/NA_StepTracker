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
import androidx.compose.ui.unit.TextUnit
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
                navController.navigate(Graph.HOME) {
                    launchSingleTop = true
                    popUpTo(0) { inclusive = true }
                }
            }) {
            Text("LogIn")
        }
        ForgotPasswordClickableText(navController)
        SignupClickableText(navController)
    }
}

@Composable
fun ClickableText(
    text: String,
    onClick: () -> Unit,
    color: Color = Color(0xFF1E88E5),
    textDecoration: TextDecoration = TextDecoration.Underline,
    fontSize: TextUnit = 16.sp,
    ){
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        textDecoration = textDecoration,
        modifier = Modifier.clickable {onClick}
    )
}
@Composable
fun LoginClickableText(navController: NavHostController){
    ClickableText("Есть аккаунт", {navController.navigate("login")})
}
@Composable
fun ForgotPasswordClickableText(navController: NavHostController){
    ClickableText("Забыли пароль?", {navController.navigate("forgot")})
}
@Composable
fun SignupClickableText(navController: NavHostController){
    ClickableText("Регистрация", {navController.navigate("signup")})
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//    NA_StepTrackerTheme {
//        LoginScreen()
//    }
//}