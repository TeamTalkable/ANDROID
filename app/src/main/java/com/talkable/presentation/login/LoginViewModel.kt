package com.talkable.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.talkable.data.SharedManager
import com.talkable.presentation.login.kakao.KakaoLoginCallback
import com.talkable.presentation.login.kakao.KakaoLoginService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val kakaoLoginService: KakaoLoginService) : ViewModel() {
    private val _isKakaoLogin = MutableStateFlow(false)
    val isKakaoLogin = _isKakaoLogin.asStateFlow()

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        KakaoLoginCallback {
            _isKakaoLogin.value = true
            Timber.d("토큰!!!! $token")
        }.handleResult(token, error)

        UserApiClient.instance.me { user, meError ->
            if (meError != null) {
                Timber.e("KakaoLogin", "사용자 정보 요청 실패", meError)
            } else if (user != null) {
                val name = user.kakaoAccount?.profile?.nickname
                SharedManager.saveNickname(name.toString())
            }
        }
    }

    fun kakaoLogin() = viewModelScope.launch {
        kakaoLoginService.startKakaoLogin(kakaoLoginCallback)
    }
}