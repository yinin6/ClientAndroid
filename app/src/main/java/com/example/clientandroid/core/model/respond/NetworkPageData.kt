package com.example.clientandroid.core.model.respond

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPageData<T>(
    val data: List<T>? = null,
    val pagination: NetworkPageMeta? = null,
)