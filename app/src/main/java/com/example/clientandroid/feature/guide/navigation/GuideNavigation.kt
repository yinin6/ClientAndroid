package com.example.clientandroid.feature.guide.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.clientandroid.feature.guide.GuideRoute
import com.example.clientandroid.feature.guide.GuideScreen
import com.example.clientandroid.feature.splash.SplashRoute
import com.example.clientandroid.feature.splash.navigation.SPLASH_ROUTE
import com.example.clientandroid.ui.navigation.myComposable

const val GUIDE_ROUTE = "guide"

/**
 * 跳转界面
 */
fun NavController.navigateToGuide() {
    navigate(GUIDE_ROUTE)
}

/**
 * 配置导航
 */
fun NavGraphBuilder.guideScreen(toBack:()->Unit): Unit {
    myComposable(GUIDE_ROUTE) {
        GuideRoute (
            toBack = toBack
        ) }

}