package com.example.clientandroid.core.model.respond

import kotlinx.serialization.Serializable


@Serializable
data class NetworkResponse<T>(
    val status:Int = 0,
    val message:String ?= null,
    val data:T? = null
)
