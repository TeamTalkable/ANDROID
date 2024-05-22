package com.talkable

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import timber.log.Timber

class TalkableApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setTimber()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "{NATIVE_APP_KEY}")
    }

    private fun setTimber() {
        Timber.plant(Timber.DebugTree())
    }
}