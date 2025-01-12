package com.example.clientandroid.feature.login


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientandroid.R
import com.example.clientandroid.core.model.LoginResponse
import com.example.clientandroid.core.model.respond.NetworkResponse
import com.example.clientandroid.feature.discovery.DiscoveryViewModel
import kotlinx.coroutines.delay


@Composable
fun LoginRoute(
    toMain : (username: String) -> Unit = {},
    toRegister : () -> Unit = {},
){
    val viewModel: LoginViewModel = viewModel()
    val loginRespond by viewModel.loginRespond.collectAsState()

    LoginScreen(
        toMain = toMain,
        login =  viewModel::login,
        loginRespond = loginRespond,
        toRegister = toRegister
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    toMain : (username: String) -> Unit = {},
    login : (username: String, password: String) -> Unit = { _, _ -> },
    loginRespond : NetworkResponse<LoginResponse>? = null,
    toRegister : () -> Unit = {},
) {

    var username by remember { mutableStateOf("asd") }
    var password by remember { mutableStateOf("asd") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val sharedPreferences = remember { context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE) }
    val temp = sharedPreferences.getString("username", "")
    if (temp != null && temp.isNotBlank() && temp != "") {
        Log.d("LoginScreen", "LoginScreen:$temp ")
        toMain(temp)
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val logoTopPadding = screenHeight * 0.12f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // 添加 Logo
            Image(
                painter = painterResource(id = R.drawable.icon_logo), // 替换为你的 Logo 资源 ID
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(320.dp) // 设置 Logo 的大小
                    .padding(top = logoTopPadding, bottom = 60.dp)
            )
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

            Button(
                onClick = { toRegister() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
            ) {
                Text("注册")
            }

            if (isLoading) {
                CircularProgressIndicator()
            }

            LaunchedEffect(loginRespond){
                isLoading = false
                if (loginRespond != null) {
                    when (loginRespond.status) {
                        200 -> {
                            Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show()
                            sharedPreferences.edit().putString("username", username).apply()
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