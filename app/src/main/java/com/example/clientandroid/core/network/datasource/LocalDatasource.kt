package com.example.clientandroid.core.network.datasource

import com.example.clientandroid.core.config.Config
import com.example.clientandroid.core.model.Json2Poetry
import com.example.clientandroid.core.model.LoginRequest
import com.example.clientandroid.core.model.LoginResponse
import com.example.clientandroid.core.model.User
import com.example.clientandroid.core.model.respond.NetworkResponse
import com.example.clientandroid.core.network.retrofit.LocalApiService
import com.example.clientandroid.core.network.retrofit.MyNetworkApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object LocalDatasource {
    private val serviceLocal = Retrofit.Builder()
        .baseUrl(Config.LOGIN_URL)
        .addConverterFactory(
            Json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()))
        .build()
        .create(LocalApiService::class.java)

    suspend fun login(username: String, password: String):  NetworkResponse<LoginResponse>{
        val request = LoginRequest(username, password)

        return serviceLocal.login(request)
    }
}