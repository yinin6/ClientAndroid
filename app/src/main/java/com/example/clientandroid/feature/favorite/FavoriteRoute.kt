package com.example.clientandroid.feature.favorite

import android.content.Context
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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.clientandroid.feature.favorite.component.ItemFavorite
import com.example.clientandroid.feature.guide.navigation.DISCOVERY_ROUTE
import com.example.clientandroid.feature.guide.navigation.MAIN_ROUTE
import com.example.clientandroid.feature.guide.navigation.navigateToPoetryDetail
import com.example.clientandroid.feature.login.navigation.navigateToLogin


@Composable
fun FavoriteRoute(
    navController: NavController
){
    val viewModel:DiscoveryViewModel = viewModel()
    val poetry by viewModel.poetry.collectAsState()
    val favoritePoetry by viewModel.favoritePoetry.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", "default_value")


//
//    LaunchedEffect(username) {
//        Log.d("DiscoveryRoute", "lunched $username")
//        viewModel.setUserID(username!!)
//    }


    FavoriteScreen(
        toSearch = {

//            viewModel.refreshPoems()
            sharedPreferences.edit().clear().apply()
            navController.navigateToLogin()
                   },
        poetryList = favoritePoetry,
        isLoading = isLoading,
        navController = navController,
        viewModel = viewModel
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    poetryList: List<PoetryData> = emptyList(),
    toSearch :() -> Unit = {},
    isLoading : Boolean = false,
    navController: NavController? = null,
    viewModel: DiscoveryViewModel = viewModel()
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
                    ItemFavorite(
                        poetry = it,
                        onClick = {
                            it.origin?.let { it1 -> navController?.navigateToPoetryDetail(it1) }
                        }
                        ,
                        delFavorite = {
                            it.let { it -> viewModel.removeFavorite(it) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MyDiscoveryTopBar(ExitToApp: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // 当用户点击对话框外部或按返回键时，关闭对话框
                showDialog = false
            },
            title = {
                Text(text = "确认退出")
            },
            text = {
                Text(text = "您确定要退出吗？")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // 用户确认退出，调用 ExitToApp
                        ExitToApp()
                        showDialog = false
                    }
                ) {
                    Text("确认")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // 用户取消退出，关闭对话框
                        showDialog = false
                    }
                ) {
                    Text("取消")
                }
            }
        )
    }



    TopAppBar(

        title = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "喜欢",
                )
            }
        },
        actions = {
            IconButton(onClick = {
                showDialog = true
            }) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Menu",
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
    FavoriteScreen(
    )
}