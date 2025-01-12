package com.example.clientandroid.core.network.retrofit

import com.example.clientandroid.core.config.Config
import com.example.clientandroid.core.model.Favorite
import com.example.clientandroid.core.model.Json2Poetry
import com.example.clientandroid.core.model.Note
import com.example.clientandroid.core.model.respond.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface MyNetworkApiService {
    @GET("poetry")
    @Headers("X-User-Token: ${Config.POETRY_TOKEN}")
    suspend fun getPoetry(): Json2Poetry


    @GET("poetryList/{n}")
    suspend fun getPoetryOfNum(@Path("n") username: Int): List<Json2Poetry>


    @GET("getFavoritesList/{username}")
    suspend fun getUserFavoritesList(@Path("username") username: String):NetworkResponse<List<String?>?>

    @GET("getFavorites/{username}")
    suspend fun getUserFavorites(@Path("username") username: String):List<Json2Poetry?>?

    @POST("removeFavorites")
    suspend fun removeFavorites(@Body favorite:Favorite): NetworkResponse<Favorite>


    @POST("addFavorites")
    suspend fun addFavorites(@Body favorite:Favorite): NetworkResponse<Favorite>


}