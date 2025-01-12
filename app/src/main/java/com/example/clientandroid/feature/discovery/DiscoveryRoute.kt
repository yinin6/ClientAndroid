package com.example.clientandroid.feature.guide

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.clientandroid.core.model.DailyHot
import com.example.clientandroid.core.model.PoetryData
import com.example.clientandroid.core.ui.PreviewData
import com.example.clientandroid.feature.discovery.DiscoveryViewModel
import com.example.clientandroid.feature.discovery.component.ItemHot
import com.example.clientandroid.feature.guide.navigation.DISCOVERY_ROUTE
import com.example.clientandroid.feature.guide.navigation.MAIN_ROUTE
import com.example.clientandroid.feature.guide.navigation.navigateToPoetryDetail


@Composable
fun DiscoveryRoute(
    navController: NavController

){
    val viewModel:DiscoveryViewModel = viewModel()
    val poetry by viewModel.poetry.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // 监听导航状态
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.route == "$MAIN_ROUTE/{username}") {
                // 刷新数据
                viewModel.refreshPoems()
            }
        }
    }

   DiscoveryScreen(
        toSearch = { viewModel.refreshPoems() },
        poetryList = poetry,
       isLoading = isLoading,
        navController = navController
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    poetryList: List<PoetryData> = emptyList(),
    dailyHots: List<DailyHot> = emptyList(),
    toSearch :() -> Unit = {},
    isLoading : Boolean = false,
    navController: NavController? = null
){
    Scaffold (
        topBar = {
            MyDiscoveryTopBar(toSearch)
        },
        containerColor = Color.White
    )
    {
        paddingValues ->

        if (isLoading) {
            LoadingIndicator()
            return@Scaffold
        }else {
            LazyColumn (
                contentPadding = PaddingValues(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                items(poetryList) {
                    ItemHot(
                        poetry = it,
                        onClick = {
                            it.origin?.let { it1 -> navController?.navigateToPoetryDetail(it1) }
                        }
                        ,
                        toBack = {

                        }
                    )
                }
            }
        }





    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MyDiscoveryTopBar(toSearch: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { },
            ) {
                Icon(
                    imageVector = Icons.Default.Menu, contentDescription = "Back",
                    modifier = Modifier.size(30.dp)
                )

            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Discovery",
                )
            }
        },
        actions = {
            IconButton(onClick = {
                toSearch()
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh, contentDescription = "Refresh",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}


// 显示加载动画
@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator()
}



@Preview
@Composable
fun DiscoveryScreenPreview() {
    DiscoveryScreen(
        dailyHots = PreviewData.dailyHots
    )
}