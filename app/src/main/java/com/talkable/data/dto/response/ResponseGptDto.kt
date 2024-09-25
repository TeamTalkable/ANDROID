package com.talkable.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGptDto(
    @SerialName("id")
    val id: String,
    @SerialName("object")
    val objectType: String,
    @SerialName("created")
    val created: Long, //time
    @SerialName("model")
    val model: String,
    @SerialName("choices")
    val choices: List<Choice>,
    @SerialName("usage")
    val usage: Usage,
    @SerialName("system_fingerprint")
    val systemFingerprint: String? = null
)

@Serializable
data class Choice(
    @SerialName("index")
    val index: Int,
    @SerialName("message")
    val message: Message,
    @SerialName("finish_reason")
    val finishReason: String,
    @SerialName("logprobs")
    val logprobs: Unit? = null
)

@Serializable
data class Message(
    @SerialName("role")
    val role: String,
    @SerialName("content")
    val content: String,
    @SerialName("refusal")
    val refusal: Unit? = null

)

@Serializable
data class Usage(
    @SerialName("prompt_tokens")
    val promptTokens: Int,
    @SerialName("completion_tokens")
    val completionTokens: Int,
    @SerialName("total_tokens")
    val totalTokens: Int,
    @SerialName("completion_tokens_details")
    val tokenDetail: Token? = null
)

@Serializable
data class Token(
    @SerialName("reasoning_tokens")
    val totalTokens: Int? = null
)
