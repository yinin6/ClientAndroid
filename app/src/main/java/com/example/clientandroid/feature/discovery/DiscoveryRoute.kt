package com.example.clientandroid.feature.guide

import android.net.wifi.aware.DiscoverySession
import androidx.compose.foundation.background
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
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.clientandroid.core.model.DailyHot
import com.example.clientandroid.core.model.User
import com.example.clientandroid.core.ui.PreviewData
import com.example.clientandroid.feature.discovery.component.ItemHot

@Composable
fun DiscoveryRoute(

){
    DiscoveryScreen(
        dailyHots = PreviewData.dailyHots,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    dailyHots: List<DailyHot> = emptyList(),
    toSearch :() -> Unit = {},
){
    Scaffold (
        topBar = {
            MyDiscoveryTopBar(toSearch)
        },
        containerColor = Color.White
    )
    {
        paddingValues ->
        LazyColumn (
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())


        ) {
                items(dailyHots) {
                    ItemHot(data = it)
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

@Preview
@Composable
fun DiscoveryScreenPreview() {
    DiscoveryScreen(
        dailyHots = PreviewData.dailyHots
    )
}