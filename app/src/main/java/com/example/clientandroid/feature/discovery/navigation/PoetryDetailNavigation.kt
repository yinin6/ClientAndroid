package com.example.clientandroid.feature.guide.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clientandroid.core.model.Json2Poetry
import com.example.clientandroid.core.model.PoetryOrigin
import com.example.clientandroid.feature.discovery.DetailRoute
import com.example.clientandroid.feature.guide.DiscoveryRoute
import com.example.clientandroid.feature.guide.GuideRoute
import com.example.clientandroid.feature.guide.GuideScreen
import com.example.clientandroid.feature.splash.SplashRoute
import com.example.clientandroid.feature.splash.navigation.SPLASH_ROUTE
import com.example.clientandroid.ui.navigation.myComposable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val POETRY_DETAIL_ROUTE = "POETRY_DETAIL_ROUTE"

/**
 * 跳转界面
 */
fun NavController.navigateToPoetryDetail(poetry: PoetryOrigin) : Unit {
    val poetryJson = Json.encodeToString(poetry)
    Log.d("navigateToPoetryDetail", "encodeToString: $poetryJson")
    navigate("$POETRY_DETAIL_ROUTE/$poetryJson")
}


/**
 * 配置导航
 */
fun NavGraphBuilder.poetryDetailScreen(toBack : () -> Unit, navController: NavController): Unit {
    myComposable(
        "$POETRY_DETAIL_ROUTE/{poetryId}",
        arguments = listOf(navArgument("poetryId") { type = NavType.StringType })
        ) {
            backStackEntry ->
        val poetryId = backStackEntry.arguments?.getString("poetryId")
        println(poetryId)
        DetailRoute(poetryId, toBack, navController = navController)
    }
}