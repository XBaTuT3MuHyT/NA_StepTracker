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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.na_steptracker.graphs.BottomBarScreen
import com.example.na_steptracker.graphs.Graph

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("LogIn screen")
        AuthSpacer()
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
    color: Color = Color(0xFF1E88E5),
    textDecoration: TextDecoration = TextDecoration.Underline,
    fontSize: TextUnit = 16.sp,
    navController: NavController,
    route: String
    ){
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        textDecoration = textDecoration,
        modifier = Modifier.clickable {
            navController.navigate(route){
                launchSingleTop = true
                popUpTo("login")
            }
        }
    )
}
@Composable
fun LoginClickableText(navController: NavController){
    ClickableText("Есть аккаунт", route = "login", navController = navController)
}
@Composable
fun ForgotPasswordClickableText(navController: NavController){
    ClickableText("Забыли пароль?", route = "forgot", navController = navController)
}
@Composable
fun SignupClickableText(navController: NavController){
    ClickableText("Регистрация", route = "signup", navController = navController)
}
@Composable
fun AuthSpacer(
    height: Dp = 300.dp
){
    Spacer(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
    )
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//    NA_StepTrackerTheme {
//        LoginScreen()
//    }
//}