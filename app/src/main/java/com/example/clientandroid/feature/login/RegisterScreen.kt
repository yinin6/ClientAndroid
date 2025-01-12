package com.example.clientandroid.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun RegisterRoute(
    toLogin: () -> Unit = {},

){
    val viewModel: LoginViewModel = viewModel()
    val registerRespond by viewModel.registerRespond.collectAsState()
    RegisterScreen(toLogin,viewModel::register,registerRespond)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    toLogin: () -> Unit = {},
    register: (username: String, password: String) -> Unit = { _, _ -> },
    registerRespond: NetworkResponse<LoginResponse>? = null,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
                text = "注册",
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
                    .padding(bottom = 16.dp),
                enabled = !isLoading,
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("确认密码") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                enabled = !isLoading,
            )

            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                        if (password == confirmPassword) {
                            isLoading = true
                            register(username, password)
                        } else {
                            Toast.makeText(context, "密码不一致！", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
            ) {
                Text("注册")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { toLogin() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
            ) {
                Text("返回登录")
            }

            if (isLoading) {
                CircularProgressIndicator()
            }

            LaunchedEffect(registerRespond) {
                isLoading = false
                if (registerRespond != null) {
                    when (registerRespond.status) {
                        201 -> {
                            Toast.makeText(context, "注册成功！", Toast.LENGTH_SHORT).show()
                            toLogin()
                        }
                        0 -> {}
                        else -> {
                            Toast.makeText(context, "注册失败！${registerRespond.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}


@Preview(name = "Register Screen", showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}