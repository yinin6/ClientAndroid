package com.example.clientandroid.feature.discovery

import android.util.Log
import com.example.clientandroid.core.model.DailyHot
import com.example.clientandroid.core.ui.PreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DiscoveryViewModel {

    /**
     * 商品列表
     */
    private val _datum = MutableStateFlow<List<DailyHot>>(emptyList())
    val datum: StateFlow<List<DailyHot>> = _datum

    init {
        _datum.value = PreviewData.dailyHots
        testJSON()
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



}