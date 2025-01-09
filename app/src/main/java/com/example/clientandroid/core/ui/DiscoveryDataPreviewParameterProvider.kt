package com.example.clientandroid.core.ui

import com.example.clientandroid.core.model.DailyHot


object PreviewData {
    val hot = DailyHot(
        id = 1,
        title = "标题",
        cover = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png",
        url = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png",
    )

    val dailyHots = mutableListOf<DailyHot>()
    init {
        for (i in 1..20) {
            dailyHots.add(
                DailyHot(
                    id = i,
                    title = "标题 $i",
                    cover = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png",
                    url = "https://example.com/$i",
                    description = "描述 $i",
                    category = "类别 $i",
                )
            )
        }
    }
}




