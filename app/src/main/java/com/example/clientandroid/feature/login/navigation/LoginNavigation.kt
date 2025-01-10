package com.example.clientandroid.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.clientandroid.feature.login.LoginScreen
import com.example.clientandroid.feature.splash.navigation.SPLASH_ROUTE
import com.example.clientandroid.ui.navigation.myComposable

const val LOGIN_ROUTE = "login"

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

/**
 * 配置导航
 */
fun NavGraphBuilder.loginScreen(login : () -> Unit): Unit {
    myComposable(LOGIN_ROUTE) {
        LoginScreen(login)
    }
}