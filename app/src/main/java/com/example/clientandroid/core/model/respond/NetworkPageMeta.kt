package com.example.clientandroid.core.model.respond

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPageMeta(
    val page: Int? = null,
    val pageSize: Int? = null,
    val total: Int? = null,
    val totalPage: Int? = null,
    val next : Int? = null,
)
