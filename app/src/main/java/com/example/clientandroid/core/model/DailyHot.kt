package com.example.clientandroid.core.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyHot(
    val id: Int? = null,
    val title: String? = null,
    val cover: String? = null,
    val url: String? = null,
    val description: String? = null,
    val category: String? = null,
    val categoryId: Int? = null,
)