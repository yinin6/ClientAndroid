package com.example.clientandroid.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientandroid.feature.guide.GuideRoute
import com.example.clientandroid.feature.splash.SplashRoute

@Composable
fun MyApp (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashRoute(
            toGuide = {
                navController.navigate("guide")
            }
        ) }
        composable("guide") { GuideRoute(
            toBack ={
                navController.navigate("splash")
            }
        ) }
        // Add more destinations similarly.
    }
}

