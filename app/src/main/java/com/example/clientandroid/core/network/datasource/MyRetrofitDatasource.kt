package com.example.clientandroid.core.network.datasource

import com.example.clientandroid.core.config.Config
import com.example.clientandroid.core.model.Json2Poetry
import com.example.clientandroid.core.network.retrofit.MyNetworkApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
/**
 * Retrofit 数据源
 */
object MyRetrofitDatasource {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // 设置日志级别
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val service = Retrofit.Builder()
        .baseUrl(Config.POETRY_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            Json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()))
        .build()
        .create(MyNetworkApiService::class.java)

    suspend fun poetry(): Json2Poetry {
        return service.getPoetry()
    }
}