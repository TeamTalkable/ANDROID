package com.talkable.data.api

import com.talkable.data.dto.request.RequestPronunciationDto
import com.talkable.data.dto.response.ResponsePronunciationDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PronunciationApiService {
    @POST("/WiseASR/Pronunciation")
    suspend fun getPronunciationResult(@Body requestBody: RequestPronunciationDto): ResponsePronunciationDto
}