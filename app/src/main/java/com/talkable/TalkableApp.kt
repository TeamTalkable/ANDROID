package com.talkable

import android.app.Application
import timber.log.Timber

class TalkableApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setTimber()
    }

    private fun setTimber() {
        Timber.plant(Timber.DebugTree())
    }
}