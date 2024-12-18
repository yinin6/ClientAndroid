package com.example.clientandroid.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.clientandroid.feature.splash.SplashRoute
import com.example.clientandroid.ui.navigation.myComposable

const val SPLASH_ROUTE = "splash"

/**
 * 跳转界面
 */
fun NavController.navigateToSplash() {
    navigate(SPLASH_ROUTE)
}

/**
 * 配置导航,扩展函数，添加splashScreen，使得myapp可以调用
 */
fun NavGraphBuilder.splashScreen(toGuide:()->Unit): Unit {
    myComposable(SPLASH_ROUTE) {
        SplashRoute(
        toGuide =toGuide
    ) }
    
}