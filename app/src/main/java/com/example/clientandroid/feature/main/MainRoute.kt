package com.example.clientandroid.feature.guide

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientandroid.core.design.component.MyNavigationBar

@Composable
fun MainRoute(){
    MainScreen()
}


@Composable
fun MainScreen(

){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MainContent(
            modifier = Modifier.weight(1f)
        )
        MyNavigationBar(
            modifier = Modifier.height(80.dp)
        )



    }



}

@Composable
fun MainContent(
    modifier: Modifier = Modifier
) {
    Text(
        "aaaa",
        modifier = modifier)
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview(): Unit {
    MainScreen()
}
