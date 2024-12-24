package com.example.clientandroid.feature.splash


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientandroid.R
import com.example.clientandroid.core.design.theme.ClientAndroidTheme


@Composable
fun SplashRoute(
    toGuide: () -> Unit,
){

    // 创建 VM
    val viewModel: SplashViewModel = viewModel ()

    // 观察需要的值
    val timeLeft by viewModel.timeLeft.collectAsState()
    val navigateToGuide by viewModel.navigateToGuide.collectAsState()




    SplashRouteScreen(
        timeLeft = timeLeft,
        navigateToGuide = navigateToGuide,
        toGuide=toGuide,
        onSkipClick = viewModel::onSkipClick,
    )
}

@Composable
fun SplashRouteScreen(
    timeLeft: Long = 0,
    toGuide: () -> Unit = {},
    navigateToGuide:Boolean = false,
    onSkipClick: () -> Unit = {},
) {



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
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

        Button(onClick = {
            onSkipClick()
        },
            modifier = Modifier
                .padding(bottom = 50.dp)
                .align(Alignment.BottomCenter)
            ) {
            Text(
                text = "跳过 $timeLeft s",

            )
        }


    }

    if (navigateToGuide){
        LaunchedEffect(true) {
            toGuide()
        }
    }



}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashRoutePreview(): Unit {
    ClientAndroidTheme {
        SplashRouteScreen()
    }

}