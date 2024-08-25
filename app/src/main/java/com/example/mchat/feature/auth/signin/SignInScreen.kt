package com.example.mchat.feature.auth.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mchat.R


@Composable
fun SignInScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val viewModel: SignInViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    val context = LocalContext.current

    if (uiState.value == SignInState.Success) {
        navController.navigate("home")
    }

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignInState.Success -> {
                navController.navigate("home")
            }

            is SignInState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = androidx.compose.ui.graphics.Color.White),
            verticalArrangement = Arrangement.Center,


            ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
                    .background(color = androidx.compose.ui.graphics.Color.White)
                    .clip(shape = androidx.compose.foundation.shape.CircleShape)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Email") },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Password") },
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.value == SignInState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = email.isNotEmpty() && password.isNotEmpty() && (uiState.value == SignInState.Nothing || uiState.value == SignInState.Error),
                    onClick = {
                        viewModel.signIn(email, password)
                    }
                ) {
                    Text(text = "Sign In")
                }
                TextButton(
                    onClick = {
                        navController.navigate("signup")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Text(text = "Don't have an account? Sign Up")
                }
            }

        }
    }
}


@Preview
@Composable
private fun SeePreview() {
    SignInScreen(navController = rememberNavController())
}