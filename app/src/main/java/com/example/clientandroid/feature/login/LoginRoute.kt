package com.example.clientandroid.feature.login


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientandroid.core.model.LoginResponse
import com.example.clientandroid.core.model.respond.NetworkResponse
import com.example.clientandroid.feature.discovery.DiscoveryViewModel
import kotlinx.coroutines.delay


@Composable
fun LoginRoute(
    toMain : (username: String) -> Unit = {},
){
    val viewModel: LoginViewModel = viewModel()
    val loginRespond by viewModel.loginRespond.collectAsState()

    LoginScreen(
        toMain = toMain,
        login =  viewModel::login,
        loginRespond = loginRespond,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    toMain : (username: String) -> Unit = {},
    login : (username: String, password: String) -> Unit = { _, _ -> },
    loginRespond : NetworkResponse<LoginResponse>? = null,
) {

    var username by remember { mutableStateOf("asd") }
    var password by remember { mutableStateOf("asd") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "登录",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("用户名") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                enabled = !isLoading,
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("密码") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                        enabled = !isLoading,
            )
            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        isLoading = true
                        login(username, password)
                    } else {
                        Toast.makeText(context, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
            ) {
                Text("登录")
            }

            if (isLoading) {
                CircularProgressIndicator()
            }

            LaunchedEffect(loginRespond){
                delay(1000)
                isLoading = false
                if (loginRespond != null) {
                    when (loginRespond.status) {
                        200 -> {
                            Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show()
                            toMain(username)
                        }
                        0 ->{}
                        else -> {
                            Toast.makeText(context, "登录失败！${loginRespond.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }
}

@Preview(name = "Login Screen", showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}