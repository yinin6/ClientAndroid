package com.example.clientandroid.core.network.retrofit

import com.example.clientandroid.core.config.Config
import com.example.clientandroid.core.model.Json2Poetry
import retrofit2.http.GET
import retrofit2.http.Headers


interface MyNetworkApiService {
    @GET("poetry")
    @Headers("X-User-Token: ${Config.POETRY_TOKEN}")
    suspend fun getPoetry(): Json2Poetry



}