package com.talkable.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPronunciationDto(
    @SerialName("language_code")
    val languageCode: String = "english",
    @SerialName("script")
    val script: String,
    @SerialName("audio")
    val audio: String,
)
