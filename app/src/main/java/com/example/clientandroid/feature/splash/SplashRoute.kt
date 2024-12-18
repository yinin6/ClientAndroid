package com.example.clientandroid.feature.splash


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientandroid.R
import com.example.clientandroid.core.design.theme.ClientAndroidTheme


@Composable
fun SplashRoute(
    toGuide: () -> Unit,
){
    SplashRouteScreen(
        toGuide=toGuide
    )
}

@Composable
fun SplashRouteScreen(toGuide: () -> Unit={}) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(id= R.drawable.a), contentDescription = "")

        Text(text = "Hello World !",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = 100.dp)
                .align(Alignment.BottomCenter)
                .clickable {
                toGuide()
            },
        )

    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashRoutePreview(): Unit {
    ClientAndroidTheme {
        SplashRouteScreen()
    }

}