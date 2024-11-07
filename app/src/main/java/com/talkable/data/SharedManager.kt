package com.talkable.data

import android.content.Context
import android.content.SharedPreferences

object SharedManager {
    private const val PREF_NAME = "my_shared_preferences"
    private const val AUTO_LOGIN = "auto_login"
    private const val NICKNAME = "nickname"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveNickname(value: String) {
        sharedPreferences.edit().putString(NICKNAME, value).apply()
    }

    fun saveIsLogin(value: Boolean) {
        sharedPreferences.edit().putBoolean(AUTO_LOGIN, value).apply()
    }

    fun getNickname(): String? {
        return sharedPreferences.getString(NICKNAME, null)
    }

    fun getBoolean(): Boolean {
        return sharedPreferences.getBoolean(AUTO_LOGIN, false)
    }
}