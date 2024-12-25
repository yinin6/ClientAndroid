package com.example.clientandroid.feature.splash

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel: ViewModel () {
    /**
     * 计时器
     */
    private var timer: CountDownTimer? = null

    /**
     * 倒计时
     */
    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft: StateFlow<Long> = _timeLeft

    /**
     * 跳转
     */
    private val _toGuide = MutableStateFlow(false)
    val navigateToGuide: StateFlow<Boolean> = _toGuide

    init {
        startCountDown()
    }

    private fun startCountDown() {
         timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = millisUntilFinished / 1000 + 1
            }
            override fun onFinish() {
                _toGuide.value = true
            }
        }.start()
    }

    /**
     * 执行下一步操作
     */
    fun onSkipClick() {
        timer?.cancel()
        _toGuide.value = true
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}
