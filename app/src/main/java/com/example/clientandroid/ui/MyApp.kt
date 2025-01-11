package com.example.clientandroid.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientandroid.feature.guide.GuideRoute
import com.example.clientandroid.feature.guide.GuideScreen
import com.example.clientandroid.feature.guide.navigation.GUIDE_ROUTE
import com.example.clientandroid.feature.guide.navigation.guideScreen
import com.example.clientandroid.feature.guide.navigation.mainScreen
import com.example.clientandroid.feature.guide.navigation.navigateToGuide
import com.example.clientandroid.feature.guide.navigation.navigateToMain
import com.example.clientandroid.feature.guide.navigation.navigateToPoetryDetail
import com.example.clientandroid.feature.guide.navigation.noteDetailScreen
import com.example.clientandroid.feature.guide.navigation.poetryDetailScreen
import com.example.clientandroid.feature.login.navigation.LOGIN_ROUTE
import com.example.clientandroid.feature.login.navigation.loginScreen
import com.example.clientandroid.feature.splash.SplashRoute
import com.example.clientandroid.feature.splash.navigation.SPLASH_ROUTE
import com.example.clientandroid.feature.splash.navigation.navigateToSplash
import com.example.clientandroid.feature.splash.navigation.splashScreen

@Composable
fun MyApp (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LOGIN_ROUTE) {
        splashScreen (
            toGuide = navController::navigateToGuide,
            toMain = navController::navigateToMain
        )
        guideScreen (
            toBack = navController::popBackStack
        )

        mainScreen(navController)

        loginScreen(
            toMain = navController::navigateToMain
        )

        poetryDetailScreen()

        noteDetailScreen()
    }
}

