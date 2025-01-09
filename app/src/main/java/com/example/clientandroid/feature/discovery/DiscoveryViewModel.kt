package com.example.clientandroid.feature.discovery

import com.example.clientandroid.core.model.DailyHot
import com.example.clientandroid.core.ui.PreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DiscoveryViewModel {

    /**
     * 商品列表
     */
    private val _datum = MutableStateFlow<List<DailyHot>>(emptyList())
    val datum: StateFlow<List<DailyHot>> = _datum

    init {
        _datum.value = PreviewData.dailyHots
    }

}