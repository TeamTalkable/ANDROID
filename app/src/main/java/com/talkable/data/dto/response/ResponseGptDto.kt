package com.talkable.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

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
    val systemFingerprint: String? = null,
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
    val logprobs: JsonObject? = null,
)

@Serializable
data class Message(
    @SerialName("role")
    val role: String,
    @SerialName("content")
    val content: String,
    @SerialName("refusal")
    val refusal: JsonObject? = null,
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
    val tokenDetail: Token? = null,
    @SerialName("prompt_tokens_details")
    val promptToken: PromptToken? = null,
)

@Serializable
data class Token(
    @SerialName("reasoning_tokens")
    val totalTokens: Int? = null,
    @SerialName("audio_tokens")
    val audioTokens: Int? = null,
    @SerialName("accepted_prediction_tokens")
    val acceptedTokens: Int? = null,
    @SerialName("rejected_prediction_tokens")
    val rejectedTokens: Int? = null,
)

@Serializable
data class PromptToken(
    @SerialName("cached_tokens")
    val cachedTokens: Int? = null,
    @SerialName("audio_tokens")
    val audioTokens: Int? = null,
)
