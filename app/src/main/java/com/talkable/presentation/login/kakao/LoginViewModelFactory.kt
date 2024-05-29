package com.talkable.presentation.login.kakao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.talkable.presentation.login.LoginViewModel

class LoginViewModelFactory(private val kakaoLoginService: KakaoLoginService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(kakaoLoginService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}