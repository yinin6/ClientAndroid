package com.example.clientandroid.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.clientandroid.feature.login.LoginRoute
import com.example.clientandroid.feature.login.LoginScreen
import com.example.clientandroid.feature.login.RegisterRoute
import com.example.clientandroid.feature.login.RegisterScreen
import com.example.clientandroid.feature.splash.navigation.SPLASH_ROUTE
import com.example.clientandroid.ui.navigation.myComposable

const val LOGIN_ROUTE = "login"
const val REG_ROUTE = "REG_ROUTE"

/**
 * 跳转界面
 */
fun NavController.navigateToLogin() {
    navigate(LOGIN_ROUTE){
        launchSingleTop = true
        popUpTo(SPLASH_ROUTE) {
            inclusive = true
        }
    }
}


fun NavController.navigateToRegister() {
    navigate(REG_ROUTE)
}

/**
 * 配置导航
 */
fun NavGraphBuilder.loginScreen(toMain : (username: String) -> Unit, toRegister : () -> Unit): Unit {
    myComposable(LOGIN_ROUTE) {
        LoginRoute(toMain = toMain, toRegister = toRegister)
    }
}

fun NavGraphBuilder.registerScreen(toLogin : () -> Unit): Unit {
    myComposable(REG_ROUTE) {

        RegisterRoute(toLogin = toLogin)
    }
}