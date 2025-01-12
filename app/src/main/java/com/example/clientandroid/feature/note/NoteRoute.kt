package com.example.clientandroid.feature.guide

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.clientandroid.core.model.Note
import com.example.clientandroid.feature.guide.navigation.navigateToNoteDetailScreen
import com.example.clientandroid.feature.note.NoteViewModel
import com.example.clientandroid.util.Base64ToBitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.example.clientandroid.feature.guide.navigation.MAIN_ROUTE
import com.example.clientandroid.feature.guide.navigation.NOTE_DETAIL_ROUTE
import kotlinx.coroutines.delay

@Composable
fun NoteRoute(
    navController: NavController
){
    val viewModel: NoteViewModel = viewModel()
    val notes by viewModel.notes.collectAsState()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", "default_value")

    viewModel.getUserNote(username!!)


    // 监听导航状态
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.route == "$MAIN_ROUTE/{username}") {
                // 刷新数据
            }
        }
    }

    Scaffold(
        topBar = {
            MyTopBar(toSearch = { })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigateToNoteDetailScreen("asd")
                    viewModel.newNote()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
        ) {
            items(notes) { note ->
                NoteItem(note = note , delNote = viewModel::delNote, toEdit = {
                    navController.navigateToNoteDetailScreen("asd")
                    viewModel.replayNote(note)
                } )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun NoteItem(note: Note, delNote : (Note) -> Unit = {}, toEdit : () -> Unit = {}) { Unit
    var showDialog by remember { mutableStateOf(false) }
    // 控制是否显示卡片的状态
    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        exit = fadeOut() + shrinkVertically(), // 淡出 + 垂直缩小动画
        modifier = Modifier
    ){


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = toEdit),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),

    ) {
        Box(modifier = Modifier
            .fillMaxWidth()

        ){
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "By: ${note.username}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (note.imageBase64.isNotEmpty()) {
                    val imageBitmap = Base64ToBitmap(note.imageBase64)
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()

                            .clip(MaterialTheme.shapes.medium)
                    )
                }
            }

            // 删除按钮
            IconButton(
                onClick = {
                    showDialog = true

                }, // 点击时调用外部传入的回调函数
                modifier = Modifier
                    .align(Alignment.TopEnd) // 将按钮定位在右上角
                    .padding(8.dp) // 添加内边距
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "删除",
                    tint = MaterialTheme.colorScheme.error // 使用错误色（红色）表示删除
                )
            }
        }


            // 提示框
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false }, // 点击外部或返回键时关闭提示框
                    title = { Text(text = "删除笔记") }, // 提示框标题
                    text = { Text(text = "确定要删除这条笔记吗？") }, // 提示框内容
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false // 关闭提示框
                                isVisible = false // 触发删除动画
                                delNote(note)
                            }
                        ) {
                            Text("确定", color = MaterialTheme.colorScheme.primary)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false } // 关闭提示框
                        ) {
                            Text("取消", color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                )
            }
        }

    }
}




@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MyTopBar(toSearch: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "启发",
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





