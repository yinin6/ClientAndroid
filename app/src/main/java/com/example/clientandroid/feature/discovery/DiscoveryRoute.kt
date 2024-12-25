package com.example.clientandroid.feature.guide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp

@Composable
fun DiscoveryRoute(

){
    DiscoveryScreen(
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    toSearch :() -> Unit = {},
){
    Scaffold (
        topBar = {
            MyDiscoveryTopBar(toSearch)
        },
        containerColor = Color.White

    ){
        paddingValues ->
        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues))
            {
            item {
                Text(text = "Discovery")
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