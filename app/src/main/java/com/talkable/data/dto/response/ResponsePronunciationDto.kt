package com.talkable.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePronunciationDto(
    @SerialName("request_id")
    val languageCode: String = "english",
    @SerialName("result")
    val script: String,
    @SerialName("return_object")
    val audio: PronunciationReturn,
)

@Serializable
data class PronunciationReturn(
    @SerialName("recognized")
    val recognized: String,
    @SerialName("score")
    val score: String,
)