package com.example.clientandroid.feature.discovery.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.clientandroid.core.model.DailyHot
import com.example.clientandroid.core.model.PoetryData


@Composable
fun ItemHot(
    data: DailyHot?=null,
    poetry: PoetryData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    toBack : () -> Unit = {},
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(color = Color.White)

    ) {
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(1.dp)
        ) {
            PoemCard(poetry, onClick, toBack)
        }
    }
}

// 显示古诗的卡片
@Composable
fun PoemCard(poetry: PoetryData, onClick: () -> Unit = {}, toBack : () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(8.dp),  // 卡片的圆角
        modifier = Modifier
            .padding(2.dp)  // 卡片的间距
            .clickable {
                poetry.id?.let {
                    onClick()
                }
            }

    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
        ){
            Column(modifier = Modifier
                .padding(16.dp)
                .weight(1f)
                .wrapContentHeight()
            ) {
                Text(
                    text = "${poetry.origin?.title}",
                    style = MaterialTheme.typography.titleLarge,  // 使用默认的 h6 样式
                    color = MaterialTheme.colorScheme.primary // 使用主题的主色
                )
                Text(
                    text = "${poetry.origin?.author}",
                    style = MaterialTheme.typography.bodyMedium,  // 使用正文2样式
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), // 用淡色显示作者
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "${poetry.content}",
                    style = MaterialTheme.typography.bodyMedium,  // 使用正文1样式
                    color = MaterialTheme.colorScheme.onSurface, // 正文文字颜色
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(48.dp) // 设置按钮大小
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = if (false) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = "收藏",
                    tint = if (false) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface // 使用 Material 3 的主题颜色
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ItemHotPreview() {
    ItemHot(
        data = DailyHot(
            id = 1,
            title = "title",
            cover = "cover",
            url = "url",
            description = "description",
            category = "category",
        ),
        poetry = PoetryData(
            content = "content",
            popularity = 1,
            recommendedReason = "recommendedReason",
            cacheAt = "cacheAt"
        ),
    )
}

