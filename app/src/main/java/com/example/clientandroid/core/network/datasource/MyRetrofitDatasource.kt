package com.example.clientandroid.core.network.datasource

import com.example.clientandroid.core.config.Config
import com.example.clientandroid.core.model.Favorite
import com.example.clientandroid.core.model.Json2Poetry
import com.example.clientandroid.core.model.PoetryData
import com.example.clientandroid.core.model.respond.NetworkResponse
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
        .baseUrl(Config.LOGIN_URL)
        .client(okHttpClient)
        .addConverterFactory(
            Json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()))
        .build()
        .create(MyNetworkApiService::class.java)

    suspend fun poetry(): Json2Poetry {
        return service.getPoetry()
    }


    suspend fun poetryOfNum(n: Int): List<PoetryData> {
        val result = service.getPoetryOfNum(n)
        val poetryList = mutableListOf<PoetryData>()

        for (i in result) {
            poetryList.add(i.data!!)
        }
        return poetryList
    }


    suspend fun getUserFavorites(username: String): List<PoetryData> {
        val result = service.getUserFavorites(username)
        val poetryList = mutableListOf<PoetryData>()
        if (result != null) {
            for (i in result) {
                if (i != null) {
                    poetryList.add(i.data!!)
                }
            }
        }
        return poetryList
    }

    suspend fun favorite(favorite: Favorite): NetworkResponse<Favorite> {
        return service.addFavorites(favorite)
    }


    suspend fun getUserFavoritesList(username: String): NetworkResponse<List<String?>?>{
        return service.getUserFavoritesList(username)
    }


    suspend fun getFavoritePoetry(username: String): List<PoetryData> {
        val result = service.getUserFavoritesList(username)
        return if (result.data != null) {
            val poetryList = mutableListOf<PoetryData>()
            for (i in result.data!!) {

            }
            poetryList
        } else {
            emptyList()
        }

    }

    suspend fun removeFavorites(favorite: Favorite): NetworkResponse<Favorite> {
        return service.removeFavorites(favorite)
    }
}