package com.example.balikitchenclub.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.balikitchenclub.main.MainActivity
import com.example.balikitchenclub.ui.theme.BaliKitchenClubTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaliKitchenClubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading
    val textError by viewModel.message
    var usernameValue by viewModel.username
    var passwordValue by viewModel.password

    LaunchedEffect(Unit){
        viewModel.checkUser(context)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(
            text = "Bali Kitchen Club",
            style = MaterialTheme.typography.titleSmall
        )

        if (isLoading){
            LinearProgressIndicator()
        }else{
            OutlinedTextField(
                value = usernameValue,
                onValueChange = {newValue ->
                    usernameValue = newValue
                },
                label = {
                    Text("Username")
                }
            )
            OutlinedTextField(
                value = passwordValue,
                onValueChange = {newValue ->
                    passwordValue = newValue
                },
                label = {
                    Text("Password")
                }
            )
        }

        Button(
            onClick = {
                viewModel.login(context)
            },
            content = {
                Text("Login")
            },
            enabled = !isLoading
        )
        Text(textError, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BaliKitchenClubTheme {
        Greeting()
    }
}