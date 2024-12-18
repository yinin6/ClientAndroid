package com.example.clientandroid.feature.guide

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GuideRoute(
    toBack:()-> Unit
){
    GuideScreen(
        toBack=toBack
    )
}


@Composable
fun GuideScreen(toBack:()-> Unit={}){
    Box(
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize()

    ){
        Button(onClick = toBack) {
            Text("返回")
        }
    }


}