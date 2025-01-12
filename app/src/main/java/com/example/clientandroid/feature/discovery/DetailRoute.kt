package com.example.clientandroid.feature.discovery

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientandroid.core.model.PoetryOrigin
import kotlinx.serialization.json.Json

@Composable
fun DetailRoute(poetryJson: String?, toBack: () -> Unit) {

    Log.d("DetailRoute", "poetry:$poetryJson")

    val viewModel:DiscoveryViewModel = viewModel()
    viewModel.getPoems()

    val poetry = Json.decodeFromString<PoetryOrigin>(poetryJson ?: "")
    PoetryScreen(poetry = poetry, toBack = toBack, viewModel = viewModel)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoetryScreen(poetry: PoetryOrigin, toBack: () -> Unit = {}, viewModel: DiscoveryViewModel= DiscoveryViewModel()) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background) // 设置背景颜色
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        MyDiscoveryTopBar(toBack, viewModel)
        Text(
            text = poetry.title ?: "",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Gray,
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 朝代和作者
        Text(
            text = "${poetry.dynasty} · ${poetry.author}",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 古诗内容（使用卡片样式包裹）
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                poetry.content?.forEach { line ->
                    Text(
                        text = line,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp, // 设置行高，增加可读性
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 翻译标题
        Text(
            text = "翻译",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                poetry.translate?.forEach { line ->
                    Text(
                        text = line,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MyDiscoveryTopBar(toBack: () -> Unit, viewModel: DiscoveryViewModel= DiscoveryViewModel()) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                toBack()
                viewModel.refreshPoems()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        title = {

        },
        actions = {
            IconButton(onClick = {

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
fun MyDiscoveryDetail() {
    var poetry = PoetryOrigin(
        title = "title",
        dynasty = "dynasty",
        author = "author",
    )
    PoetryScreen(poetry)
}