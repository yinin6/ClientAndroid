package com.example.clientandroid.core.network.retrofit

import com.example.clientandroid.core.model.Json2Poetry
import com.example.clientandroid.core.model.LoginRequest
import com.example.clientandroid.core.model.LoginResponse
import com.example.clientandroid.core.model.User
import com.example.clientandroid.core.model.respond.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LocalApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): NetworkResponse<LoginResponse>

}