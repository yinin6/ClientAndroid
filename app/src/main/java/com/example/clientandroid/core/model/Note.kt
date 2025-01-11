package com.example.clientandroid.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val imageBase64: String,
    val username: String
)



