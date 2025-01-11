package com.example.clientandroid.feature.guide.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.clientandroid.feature.guide.GuideRoute
import com.example.clientandroid.feature.guide.GuideScreen
import com.example.clientandroid.feature.guide.MainRoute
import com.example.clientandroid.feature.guide.MainScreen
import com.example.clientandroid.feature.splash.SplashRoute
import com.example.clientandroid.feature.splash.navigation.SPLASH_ROUTE
import com.example.clientandroid.ui.navigation.myComposable

const val MAIN_ROUTE = "main"

/**
 * 跳转界面
 */
fun NavController.navigateToMain(username : String? = "") {
    navigate("$MAIN_ROUTE/{$username}"){
        launchSingleTop = true
        popUpTo(MAIN_ROUTE)
    }
}

/**
 * 配置导航
 */
fun NavGraphBuilder.mainScreen(navController: NavController): Unit {
    myComposable(
        "$MAIN_ROUTE/{username}",
        arguments = listOf(navArgument("username") { type = NavType.StringType })
    ) {
            backStackEntry ->
        val username = backStackEntry.arguments?.getString("username")
        MainRoute(username, navController = navController)
    }
}