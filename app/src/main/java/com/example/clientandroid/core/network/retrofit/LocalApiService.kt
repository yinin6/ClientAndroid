package com.example.clientandroid.core.network.retrofit


import com.example.clientandroid.core.model.LoginRequest
import com.example.clientandroid.core.model.LoginResponse
import com.example.clientandroid.core.model.Note
import com.example.clientandroid.core.model.User
import com.example.clientandroid.core.model.respond.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LocalApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): NetworkResponse<LoginResponse>

    @POST("register")
    suspend fun register(@Body request: LoginRequest): NetworkResponse<LoginResponse>

    @POST("saveNote")
    suspend fun saveDiaryEntry(@Body note : Note): NetworkResponse<Note>

    @GET("getUserNotes/{username}")
    suspend fun getUserNotes(@Path("username") username: String): NetworkResponse<List<Note>>

    @GET("delNote/{id}")
    suspend fun delNote(@Path("id") id: Int): NetworkResponse<Note>

}