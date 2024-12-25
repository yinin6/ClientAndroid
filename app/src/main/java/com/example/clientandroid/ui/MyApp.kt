package com.example.clientandroid.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientandroid.feature.guide.GuideRoute
import com.example.clientandroid.feature.guide.GuideScreen
import com.example.clientandroid.feature.guide.navigation.GUIDE_ROUTE
import com.example.clientandroid.feature.guide.navigation.guideScreen
import com.example.clientandroid.feature.guide.navigation.navigateToGuide
import com.example.clientandroid.feature.splash.SplashRoute
import com.example.clientandroid.feature.splash.navigation.SPLASH_ROUTE
import com.example.clientandroid.feature.splash.navigation.navigateToSplash
import com.example.clientandroid.feature.splash.navigation.splashScreen

@Composable
fun MyApp (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SPLASH_ROUTE) {
        splashScreen (
            toGuide = navController::navigateToGuide
        )

        guideScreen (
            toBack = navController::popBackStack
        )
    }
}

