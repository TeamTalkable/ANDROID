package com.talkable.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.talkable.BuildConfig.GPT_API_KEY
import com.talkable.data.api.GptApiService
import com.talkable.data.api.PronunciationApiService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiFactory {

    private fun getLogOkHttpClient(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("Retrofit2", "CONNECTION INFO -> $message")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun getAuthInterceptor(key: String): Interceptor {
        return Interceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    key
                )
                .build()
            chain.proceed(request)
        }
    }

    private fun okHttpClient(key: String) = OkHttpClient.Builder()
        .addInterceptor(getAuthInterceptor(key))
        .addInterceptor(getLogOkHttpClient())
        .build()

    val retrofitGpt: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(okHttpClient("Bearer $GPT_API_KEY"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val retrofitPronunciation: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://aiopen.etri.re.kr:8000/")
            .client(okHttpClient("9d133d53-aa52-4b45-b0ae-28376e63bab1"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> createGpt(): T = retrofitGpt.create(T::class.java)

    inline fun <reified T> createPronunciation(): T = retrofitPronunciation.create(T::class.java)
}

object ServicePool {
    val gptService = ApiFactory.createGpt<GptApiService>()
    val pronunciationService = ApiFactory.createPronunciation<PronunciationApiService>()
}