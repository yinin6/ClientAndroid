package com.example.clientandroid.feature.guide

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientandroid.core.design.component.MyNavigationBar
import com.example.clientandroid.feature.main.navigation.TopLevelDestination
import kotlinx.coroutines.launch

@Composable
fun MainRoute(){
    MainScreen()
}


@Composable
fun MainScreen(

){
    // 当前选中的界面 router
    var currentDestination by rememberSaveable {
        mutableStateOf(TopLevelDestination.DISCOVERY.route)
    }
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {



        val pagerState = rememberPagerState {
            TopLevelDestination.entries.size
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .weight(1f).fillMaxWidth()) {
            when (pagerState.currentPage) {
                0 -> {
                    DiscoveryRoute()
                }
                1 -> {
                    ShortViewScreen()
                }
                2 -> {
                    MeScreen()
                }
            }
        }

        MyNavigationBar(
            modifier = Modifier,
            destination = TopLevelDestination.entries,
            currentDestination = currentDestination,
            onNavigateToDestination = {
                currentDestination = TopLevelDestination.entries[it].route
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        )



    }



}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(): Unit {
    MainScreen()
}
