package com.example.clientandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientandroid.ui.theme.ClientAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClientAndroidTheme (
//                darkTheme = true
            ){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    MyButton("click", modifier =  Modifier.padding(top = 50.dp))
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}





@Composable
fun MyButton(title:String, modifier: Modifier = Modifier ){
    Button(onClick = {
        Log.d("Button","click")
    },
        modifier = modifier.fillMaxWidth()
        ) {
        Text(title)
    }
}


@Preview(showBackground = true)
@Composable
fun buttonPreview(){
    MyButton("hello")
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClientAndroidTheme {
        Greeting("Android")
    }
}