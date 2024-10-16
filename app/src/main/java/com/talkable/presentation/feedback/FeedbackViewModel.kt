package com.talkable.presentation.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talkable.data.FirebaseFactory
import com.talkable.data.ServicePool
import com.talkable.data.dto.request.Argument
import com.talkable.data.dto.request.Message
import com.talkable.data.dto.request.RequestGptDto
import com.talkable.data.dto.request.RequestPronunciationDto
import com.talkable.presentation.feedback.model.FeedbackContainer
import com.talkable.presentation.talk.feedback.model.Learned
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import timber.log.Timber
import kotlin.math.round

class FeedbackViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FeedbackUiState>(FeedbackUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val messages = mutableListOf<Message>()
    var feedback = TalkFeedbackModel()

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
                }.onSuccess { data ->
                    updateFeedback(data)
                    _uiState.value = FeedbackUiState.PatchGptFeedbacks(data)
                }
                messages.add(Message(response.role, response.content))
                Timber.w(messages.toString())
            }.onFailure {
                _uiState.value = FeedbackUiState.Error(it.message.toString())
            }
        }
    }

    fun postFeedback() {
        FirebaseFactory.feedbackRef.child("learnedAfterAnswer")
            .setValue(feedback.learnedAfterAnswer)
        FirebaseFactory.feedbackRef.child("learnedExpression").setValue(feedback.learnedExpression)
        FirebaseFactory.feedbackRef.child("learnedGrammar").setValue(feedback.learnedGrammar)
        feedback = TalkFeedbackModel()
    }

    private fun updateFeedback(data: FeedbackContainer) {
        feedback.learnedAfterAnswer.add(
            Learned.AfterAnswer(
                afterFullAnswer = data.afterFullAnswer, afterAnswerParts = data.afterAnswerParts
            )
        )

        createLearnedListWithLabels(data.feedback, "Expression").forEach {
            feedback.learnedExpression.add(it as Learned.Expression)
        }

        createLearnedListWithLabels(data.feedback, "Grammar").forEach {
            feedback.learnedGrammar.add(it as Learned.Grammar)
        }
    }

    private fun createLearnedListWithLabels(
        feedbackMap: Map<String, List<Learned>>,
        key: String,
    ): List<Learned> {
        val resultList = mutableListOf<Learned>()

        if (feedbackMap.containsKey(key)) {
            when (key) {
                "Expression" -> {
                    resultList.addAll(
                        (feedbackMap[key] as? Collection<Learned.Expression> ?: emptyList()).map {
                            it.copy(
                                type = "표현된"
                            )
                        })
                }

                else -> {
                    resultList.addAll((feedbackMap[key] as? Collection<Learned.Grammar>
                        ?: emptyList()).map { it.copy(type = "문법") })
                }
            }

        }
        return resultList
    }

    private fun generateRequestContent(userSentence: String): String {
        return """
    Please correct the grammatical and expressive errors in the following sentence: 
    "$userSentence"
    
    1. Provide a corrected sentence that is grammatically correct and expressed more naturally.
    2. Identify one or more key parts of the sentence that were changed during the correction process.
    3. For each part, clearly point out which section of the original sentence (userSentence) was changed.
    4. Provide feedback on any expressions improved in the corrected sentence.
    5. Provide feedback on grammar improvements, and explain why the original was incorrect in Korean.
    6. For expressions, include both the English and Korean translations.
    7. Keep your response under 1000 tokens.
    8. Structure your response as follows:
    {
      "afterFullAnswer": "<corrected sentence>",
      "afterAnswerParts": ["<part1>", "<part2>"],
      "feedback": {
        "Expression": [
          {"type": "Expression","category": "<expression issue>", wordEnglish": "<expression in English>", "wordKorean": "<expression in Korean>","expressionAfterAnswer" : { "afterFullAnswer": "<expressively changed sentence>","afterAnswerParts": ["<part1>", "<part2>"]}
          }
        ],
        "Grammar": [
          {"type": "Grammar","category": "<expression issue>", "wrongGrammar": "<wrong grammar>", "correctGrammar": "<correct grammar>", "reason": "<Please explain the reason in Korean>", "grammarAfterAnswer" : { "afterFullAnswer": "<grammatically changed sentence>","afterAnswerParts": ["<part1>", "<part2>"]}
          }
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

    fun patchPronunciationEvaluation(script: String, audio: String) {
        viewModelScope.launch {
            _uiState.value = FeedbackUiState.Loading
            runCatching {
                ServicePool.pronunciationService.getPronunciationResult(
                    RequestPronunciationDto(
                        Argument(
                            languageCode = "english", script = script, audio = audio
                        )
                    )
                )
            }.onSuccess {
                _uiState.value = FeedbackUiState.PatchPronunciationFeedbacks(round(it.audio.score.toDouble()) * 100)
            }.onFailure { _uiState.value = FeedbackUiState.Error(it.message.toString()) }
        }
    }
}

sealed interface FeedbackUiState {
    data object Loading : FeedbackUiState

    data object Success : FeedbackUiState

    data object Empty : FeedbackUiState

    data class Error(val errorMessage: String) : FeedbackUiState

    data class PatchGptFeedbacks(val data: FeedbackContainer) : FeedbackUiState

    data class PatchPronunciationFeedbacks(val score: Double) : FeedbackUiState
}
