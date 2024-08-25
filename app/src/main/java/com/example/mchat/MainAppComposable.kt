package com.example.mchat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mchat.feature.auth.Home.HomeScreen
import com.example.mchat.feature.auth.signin.SignInScreen
import com.example.mchat.feature.auth.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun MainApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val startDestination = if (currentUser == null) "login" else "home"

        NavHost(navController = navController, startDestination = startDestination) {
            composable("login") {
                SignInScreen(navController)
            }
            composable("signup") {
                SignUpScreen(navController)
            }
            composable("home") {
                HomeScreen(navController)
            }
        }
    }
}