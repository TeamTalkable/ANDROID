package com.talkable.presentation.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talkable.data.ServicePool
import com.talkable.data.dto.request.Message
import com.talkable.data.dto.request.RequestGptDto
import com.talkable.presentation.feedback.model.FeedbackContainer
import com.talkable.presentation.talk.feedback.model.Learned
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import timber.log.Timber

class FeedbackViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FeedbackUiState>(FeedbackUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val messages = mutableListOf<Message>()

    private val learnedModule = SerializersModule {
        polymorphic(Learned::class) {
            subclass(Learned.Label::class, Learned.Label.serializer())
            subclass(Learned.Expression::class, Learned.Expression.serializer())
            subclass(Learned.Grammar::class, Learned.Grammar.serializer())
            subclass(Learned.Pronunciation::class, Learned.Pronunciation.serializer())
            subclass(Learned.AfterAnswer::class, Learned.AfterAnswer.serializer())
        }
    }

    private val json = Json {
        allowTrailingComma = true
        serializersModule = learnedModule
        classDiscriminator = "type"
    }

    fun patchGptFeedbacks(answer: String) {
        viewModelScope.launch {
            runCatching {
                _uiState.value = FeedbackUiState.Loading
                ServicePool.gptService.getGptAnswers(
                    RequestGptDto(
                        model = "gpt-3.5-turbo",
                        maxTokens = 1000,
                        messages = messages + Message("user", generateRequestContent(answer))
                    )
                )
            }.onSuccess {
                messages.add(Message("user", answer))
                val response = it.choices.first().message
                runCatching {
                    json.decodeFromString<FeedbackContainer>(response.content)
                }.onSuccess { data -> _uiState.value = FeedbackUiState.PatchGptFeedbacks(data) }
                messages.add(Message(response.role, response.content))
                Timber.w(messages.toString())
            }.onFailure {
                _uiState.value = FeedbackUiState.Error(it.message.toString())
            }
        }
    }

    private fun generateRequestContent(userSentence: String): String {

        return """
    Please correct the grammatical and expressive errors in the following sentence: 
    "$userSentence"
    
    1. Provide a corrected sentence.
    2. Provide two or more key parts of the corrected sentence.
    3. For each part, indicate which part was changed from the original sentence. There may be more than two parts if necessary.
    4. Give feedback on the expressions used in the corrected sentence. 
    5. Give feedback on grammar and include reason in Korean.
    6. For expressions, include both English and Korean translations.
    7. Limit your entire response to within 1000 tokens.
    8. Structure your response as follows:
    {
      "afterFullAnswer": "<corrected sentence>",
      "afterAnswerParts": ["<part1>", "<part2>"],
      "feedback": {
        "Expression": [
          {"type": "Expression","category": "<expression issue>", wordEnglish": "<expression in English>", "wordKorean": "<expression in Korean>"}
        ],
        "Grammar": [
          {"type": "Grammar","category": "<expression issue>", "wrongGrammar": "<wrong grammar>", "correctGrammar": "<correct grammar>", "reason": "<reason in Korean>"}
        ]
      },
       "nextQuestionEn": "<Generate a follow-up question based on the corrected sentence in English>",
      "nextQuestionKo": "<next question english in korean>"
    }
    """
    }

    fun setEmptyState() {
        _uiState.value = FeedbackUiState.Empty
    }

    fun setPronunciationState() {
        _uiState.value = FeedbackUiState.PatchPronunciationFeedbacks
    }
}

sealed interface FeedbackUiState {
    data object Loading : FeedbackUiState

    data object Success : FeedbackUiState

    data object Empty : FeedbackUiState

    data class Error(val errorMessage: String) : FeedbackUiState

    data class PatchGptFeedbacks(val data: FeedbackContainer) : FeedbackUiState

    data object PatchPronunciationFeedbacks : FeedbackUiState
}
