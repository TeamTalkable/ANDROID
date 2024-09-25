package com.talkable.data.api

import com.talkable.data.dto.request.RequestGptDto
import com.talkable.data.dto.response.ResponseGptDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GptApiService {
    @POST("v1/chat/completions?")
    suspend fun getGptAnswers(@Body requestBody: RequestGptDto): ResponseGptDto
}