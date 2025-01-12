package com.example.clientandroid.feature.discovery

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientandroid.core.config.Config
import com.example.clientandroid.core.model.DailyHot
import com.example.clientandroid.core.model.Favorite
import com.example.clientandroid.core.model.PoetryData
import com.example.clientandroid.core.model.PoetryOrigin
import com.example.clientandroid.core.network.datasource.MyRetrofitDatasource
import com.example.clientandroid.core.ui.PreviewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import kotlin.math.log

class DiscoveryViewModel: ViewModel() {

    private val _userID = MutableStateFlow("")
    /**
     * 商品列表
     */
    private val _datum = MutableStateFlow<List<DailyHot>>(emptyList())
    val datum: StateFlow<List<DailyHot>> = _datum

    private val _poetry = MutableStateFlow<List<PoetryData>>(emptyList())
    val poetry: StateFlow<List<PoetryData>> = _poetry

    private val _favoritePoetry = MutableStateFlow<List<PoetryData>>(emptyList())
    val favoritePoetry: StateFlow<List<PoetryData>> = _favoritePoetry




    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun setUserID(userID: String) {
        if (_userID.value == userID) {
            return
        }
        _userID.value = userID
        getPoetry()
    }


     fun getPoetry() : Unit{
        viewModelScope.launch {
            _isLoading.value = true
            val result = MyRetrofitDatasource.poetryOfNum(4)
            Log.d(TAG, "getPoetry: $result")
            val fl =  MyRetrofitDatasource.getUserFavoritesList(_userID.value)
            for (poetry in result) {
                if (fl.data == null) {
                    continue
                }
                for (favorite in fl.data) {
                    if (poetry.id == favorite) {
                        poetry.favorite = true
                    }
                }
            }

            val f = MyRetrofitDatasource.getUserFavorites(_userID.value)
            _favoritePoetry.value = f
            Log.d(TAG, "_favoritePoetry: $f")

            _poetry.value = result
            _isLoading.value = false
        }
    }

    // 刷新数据的方法
    fun refreshPoems() {
        getPoetry()
    }

    fun getPoems() {
        Log.d(TAG, "viewModelRemember: ${_poetry.value} , $_isLoading")
    }

    fun setFavorite(poem : PoetryData){
        viewModelScope.launch {
            val result = MyRetrofitDatasource.favorite(Favorite(userID = _userID.value,poem.id))
            for (poetry in _poetry.value) {
                if (poetry.id == poem.id) {

                }
            }
            Log.d(TAG, "getFavorite: $result")
        }
    }

    fun getFavorite() {
        viewModelScope.launch {
            val result = MyRetrofitDatasource.getUserFavoritesList(_userID.value)
            Log.d(TAG, "getFavorite: $result")
        }
    }

    fun removeFavorite(poem : PoetryData) {
        viewModelScope.launch {
            Log.d(TAG, "removeFavorite: $poem")
            val result = MyRetrofitDatasource.removeFavorites(Favorite(userID = _userID.value,poem.id))
            Log.d(TAG, "removeFavorite: $result")
        }
    }




    // 测试 ---------------------------------------------------------------------------------
    private fun testRetrofitGet() {
        viewModelScope.launch {
            val result = MyRetrofitDatasource.poetry()
            Log.d(TAG, "testRetrofitGet: $result")
        }
    }

    private fun testJSON() {
        val json = Json.encodeToString(PreviewData.dailyHots[0])
        Log.d(TAG, "encodeToString: $json")
        val dailyHot = Json.decodeFromString<DailyHot>(json)
        Log.d(TAG, "decodeFromString: $dailyHot")
    }
    companion object {
        private const val TAG = "DiscoveryViewModel"
    }


    /**
     * 测试OKHttp，新建线程调用
     */
    private fun testOKHttpGet() {
        Thread {
            val okHttpClient = okhttp3.OkHttpClient()
            val request = okhttp3.Request.Builder()
                .url(Config.POETRY_BASE_URL + "sentence")
                .addHeader("X-User-Token", Config.POETRY_TOKEN)
                .build()
            Log.d(TAG, "开始请求数据")
            val respond = okHttpClient.newCall(request).execute()
            val body = respond.body?.string()
            Log.d(TAG, "testOKHttpGet: $body")
        }.start()
    }

    private fun testOKHttpCoroutine() {
        viewModelScope.launch {
            val okHttpClient = OkHttpClient()
            val request = okhttp3.Request.Builder()
                .url(Config.POETRY_BASE_URL + "sentence")
                .addHeader("X-User-Token", Config.POETRY_TOKEN)
                .build()
            val executeRequest = executeRequest(okHttpClient, request)
            Log.d(TAG, "testOKHttpCoroutine: $executeRequest")
        }
    }

    private suspend fun executeRequest(okHttpClient: OkHttpClient, request: okhttp3.Request): String {
        return withContext(Dispatchers.IO) {
            val resultString: String = try {
                val result = okHttpClient.newCall(request).execute()
                result.body?.string() ?: ""
            } catch (e: Exception) {
                "error"
            }
            resultString
        }
    }



}