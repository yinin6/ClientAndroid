package com.example.clientandroid.feature.discovery

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientandroid.core.model.PoetryOrigin
import kotlinx.serialization.json.Json

@Composable
fun DetailRoute(poetryJson: String?) {

    Log.d("DetailRoute", "poetry:$poetryJson")

    val poetry = Json.decodeFromString<PoetryOrigin>(poetryJson ?: "")
    PoetryScreen(poetry = poetry)
}


@Composable
fun PoetryScreen(poetry: PoetryOrigin) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 标题
        Text(
            text = poetry.title ?: "",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 朝代和作者
        Text(
            text = "${poetry.dynasty} · ${poetry.author}",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 古诗内容
        poetry.content?.forEach { line ->
            Text(
                text = line,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 翻译标题
        Text(
            text = "翻译",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 翻译内容
        poetry.translate?.forEach { line ->
            Text(
                text = line,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}