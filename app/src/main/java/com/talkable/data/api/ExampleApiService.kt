package com.talkable.data.api

import com.talkable.data.dto.response.ResponseExampleDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ExampleApiService {
    companion object {
        const val API = "api"
        const val V1 = "v1"
        const val MEMBERS = "members"
    }

    // 기본
    @POST("$API/$V1/$MEMBERS")
    fun postExample1(
        @Body requestExample: Unit
    ): Call<ResponseExampleDto>

    // 코루틴
    @POST("$API/$V1/$MEMBERS")
    suspend fun postExample2(
        @Body requestExample: Unit
    ): ResponseExampleDto
}