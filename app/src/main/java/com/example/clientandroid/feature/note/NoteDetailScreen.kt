package com.example.clientandroid.feature.note

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.clientandroid.feature.discovery.DiscoveryViewModel
import com.example.clientandroid.util.Base64ToBitmap
import com.example.clientandroid.util.BitmapToBase64
import com.example.clientandroid.util.CompressBitmap
import java.io.ByteArrayOutputStream

@Composable
fun NoteDetailRoute(noteId : String? , toBack: () -> Unit, navController: NavController){
    val viewModel : NoteViewModel = viewModel(
        navController.previousBackStackEntry!!
    )

    NoteDetailScreen(viewModel, toBack)
}

@Composable
fun NoteDetailScreen(viewModel: NoteViewModel = NoteViewModel(), toBack: () -> Unit = {}) {
    val title by viewModel.title.collectAsState()
    val content by viewModel.content.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", "default_value")
    val loginRespond by viewModel.loginRespond.collectAsState()


    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val compressedBitmap = CompressBitmap(bitmap)
            val base64Image = BitmapToBase64(compressedBitmap)
            viewModel.updateImageUrl(base64Image)
        }
    }

    Scaffold (
        topBar = {
            MyTopBar(toBack = {
                if (username != null) {
                    viewModel.saveNote(userId = username)
                }
                toBack()
            }

                , addPic = { launcher.launch("image/*") }, title = username)

        },
    )
    {
            paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        BasicTextField(
                            value = title,
                            onValueChange = { viewModel.updateTitle(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.headlineMedium,
                            decorationBox = { innerTextField ->
                                if (title.isEmpty()) {
                                    Text(text = "标题",
                                        style = MaterialTheme.typography.headlineMedium)
                                }
                                innerTextField()
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        BasicTextField(
                            value = content,
                            onValueChange = { viewModel.updateContent(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyLarge,
                            decorationBox = { innerTextField ->
                                if (content.isEmpty()) {
                                    Text(text = "正文", style = MaterialTheme.typography.bodyLarge)
                                }
                                innerTextField()
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        imageUrl?.let { base64 ->
                            val bitmap = Base64ToBitmap(base64)
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .heightIn(max = 200.dp)
                                    .aspectRatio(bitmap.width.toFloat() / bitmap.height.toFloat())
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Spacer(modifier = Modifier.height(16.dp))

//                        Button(onClick = {
//                            Log.d("NoteDetailScreen", "username: $username")
//                            if (username != null) {
//                                viewModel.saveNote(userId = username)
//                            }
//                        }) {
//                            Text(text = "保存")
//                        }
                    }
                }
            }
        }

        LaunchedEffect(loginRespond){
            if (loginRespond != null) {
                when (loginRespond!!.status) {
                    200 -> {
                        Toast.makeText(context, "保存成功！", Toast.LENGTH_SHORT).show()
                    }
                    0 ->{}
                    else -> {
                        Toast.makeText(context, "保存失败！${loginRespond!!.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }




}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MyTopBar(toBack: () -> Unit, title: String ?= "", addPic: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { toBack() },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back",
                    modifier = Modifier.size(30.dp)
                )

            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "新建",
                )
            }
        },
        actions = {
            IconButton(onClick = {
                addPic()
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircle, contentDescription = "add",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}


@Preview(showBackground = true, name = "NoteScreen Preview")
@Composable
fun PreviewNoteScreen() {
    MaterialTheme {
        NoteDetailScreen()
    }
}

