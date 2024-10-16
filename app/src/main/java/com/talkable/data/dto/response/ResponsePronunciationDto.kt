package com.talkable.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePronunciationDto(
    @SerialName("return_type")
    val returnType: String,
    @SerialName("result")
    val result: Int,
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