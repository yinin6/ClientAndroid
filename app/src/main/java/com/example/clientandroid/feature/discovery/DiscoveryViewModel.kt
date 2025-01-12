package com.example.clientandroid.feature.discovery

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientandroid.core.config.Config
import com.example.clientandroid.core.model.DailyHot
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
    /**
     * 商品列表
     */
    private val _datum = MutableStateFlow<List<DailyHot>>(emptyList())
    val datum: StateFlow<List<DailyHot>> = _datum

    private val _poetry = MutableStateFlow<List<PoetryData>>(emptyList())
    val poetry: StateFlow<List<PoetryData>> = _poetry


    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

//    init {
//        _datum.value = PreviewData.dailyHots
//        testJSON()
//        testOKHttpGet()
//        testOKHttpCoroutine()
//        testRetrofitGet()
//    }

    init {
        getPoetry()
    }


    private fun getPoetry() : Unit{
        viewModelScope.launch {
            _isLoading.value = true
            val poetryList = mutableListOf<PoetryData>()
            for ( i in 0..2) {
                val result = MyRetrofitDatasource.poetry()
                result.data?.let { poetryList.add(it) }
            }
            _poetry.value = poetryList
            Log.d(TAG, "getPoetry: $poetryList")
            _isLoading.value = false
        }
    }

    // 刷新数据的方法
    fun refreshPoems() {
        getPoetry()
    }

    fun getPoems() {
        Log.d(TAG, "getPoems: ${_poetry.value} , $_isLoading")
    }



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