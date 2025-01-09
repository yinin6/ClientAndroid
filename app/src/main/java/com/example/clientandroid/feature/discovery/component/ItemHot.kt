package com.example.clientandroid.feature.discovery.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientandroid.core.model.DailyHot
import com.example.clientandroid.core.model.PoetryData

@Composable
fun ItemHot(
    data: DailyHot?=null,
    poetry: PoetryData,
    modifier: Modifier = Modifier,
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
            poetry.origin?.title?.let {
                Text(text = it,
                    minLines = 1,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleSmall,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                )
            }

            poetry.content?.let {
                Text(text = it,
                    minLines = 2,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    modifier = Modifier.padding(2.dp)
                )
            }

        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 8.dp), // 设置上下间距
        thickness = 1.dp, // 设置线条的厚度
        color = Color.Gray // 设置颜色
    )
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
        )
    )
}

