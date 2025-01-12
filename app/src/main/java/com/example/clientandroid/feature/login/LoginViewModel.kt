package com.example.clientandroid.feature.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientandroid.core.model.DailyHot
import com.example.clientandroid.core.model.LoginResponse
import com.example.clientandroid.core.model.respond.NetworkResponse
import com.example.clientandroid.core.network.datasource.LocalDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _loginRespond = MutableStateFlow<NetworkResponse<LoginResponse>?>(null)
    var loginRespond : StateFlow<NetworkResponse<LoginResponse>?> = _loginRespond

    private val _registerRespond = MutableStateFlow<NetworkResponse<LoginResponse>?>(null)
    var registerRespond : StateFlow<NetworkResponse<LoginResponse>?> = _registerRespond


    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    companion object {
        private const val TAG = "LoginViewModel"
    }

    init {
        _loginRespond.value = NetworkResponse<LoginResponse>(status = 0)
    }


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d(TAG, "login: LoginViewModel")
            _loginRespond.value = LocalDatasource.login(username, password)
            Log.d(TAG, "$loginRespond.value")
            _isLoading.value = false
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            Log.d(TAG, "login: LoginViewModel")
            _registerRespond.value = LocalDatasource.register(username, password)
            Log.d(TAG, "${_registerRespond.value}")

        }
    }



}