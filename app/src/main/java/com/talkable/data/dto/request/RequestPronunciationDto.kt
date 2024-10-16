package com.talkable.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPronunciationDto(
    @SerialName("argument")
    val argument: Argument,
)

@Serializable
data class Argument(
    @SerialName("language_code")
    val languageCode: String,
    @SerialName("script")
    val script: String,
    @SerialName("audio")
    val audio: String,
)

