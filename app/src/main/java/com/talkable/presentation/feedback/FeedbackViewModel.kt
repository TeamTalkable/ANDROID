package com.talkable.presentation.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talkable.core.type.RoleType
import com.talkable.data.FirebaseFactory
import com.talkable.data.ServicePool
import com.talkable.data.dto.request.Argument
import com.talkable.data.dto.request.Message
import com.talkable.data.dto.request.RequestGptDto
import com.talkable.data.dto.request.RequestPronunciationDto
import com.talkable.presentation.feedback.model.FeedbackContainer
import com.talkable.presentation.talk.feedback.model.Learned
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val messages = mutableListOf(Message(RoleType.SYSTEM.name.lowercase(), GPT_SYSTEM))
    var feedback = TalkFeedbackModel()
    var expressionFeedback = FeedbackContainer()
    var script = Triple("", "", "")
    var byteArray: ByteArray = byteArrayOf()
    private var talkStartTime: Long = 0L

    private val _bottomSheetMessage = MutableStateFlow<List<Message>>(emptyList())
    val bottomSheetMessage: StateFlow<List<Message>> = _bottomSheetMessage

    fun updateTalkTime(startTime: Long) {
        talkStartTime = startTime
    }

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

    fun patchGptFeedbacks(question: Pair<String, String>, answer: String) {
        viewModelScope.launch {
            val gptRequest = generateRequestContent(question.first, answer)
            runCatching {
                _uiState.value = FeedbackUiState.Loading
                ServicePool.gptService.getGptAnswers(
                    RequestGptDto(
                        model = "gpt-3.5-turbo",
                        maxTokens = 1000,
                        messages = messages + Message(
                            RoleType.USER.name.lowercase(),
                            gptRequest
                        )
                    )
                )
            }.onSuccess {
                val response = it.choices.first().message.content
                updateEntireMessage(RoleType.USER, gptRequest)
                updateEntireMessage(RoleType.ASSISTANT, response)
                runCatching {
                    json.decodeFromString<FeedbackContainer>(response)
                }.onSuccess { data ->
                    updateFeedback(data, answer)
                    _uiState.value = FeedbackUiState.PatchGptFeedbacks(data)
                    expressionFeedback = data
                    script = Triple(question.first, question.second, answer)
                }
                Timber.w(response)
            }.onFailure {
                _uiState.value = FeedbackUiState.Error(it.message.toString())
            }
        }
    }

    private fun updateEntireMessage(type: RoleType, talk: String) =
        messages.add(Message(type.name.lowercase(), talk))

    fun postFeedback() {
        val elapsedTimeMillis = System.currentTimeMillis() - talkStartTime
        val elapsedTimeMinutes = elapsedTimeMillis / (1000.0 * 60)
        val roundedElapsedTimeMinutes = Math.round(elapsedTimeMinutes * 100) / 100.0
        viewModelScope.launch { delay(1000) }

        FirebaseFactory.feedbackRef.child("talkTime")
            .setValue(roundedElapsedTimeMinutes.toString())
        FirebaseFactory.feedbackRef.child("learnedAfterAnswer")
            .setValue(feedback.learnedAfterAnswer)
        FirebaseFactory.feedbackRef.child("learnedExpression").setValue(feedback.learnedExpression)
        FirebaseFactory.feedbackRef.child("learnedGrammar").setValue(feedback.learnedGrammar)
        feedback = TalkFeedbackModel()
        _uiState.value = FeedbackUiState.Empty
    }

    private fun updateFeedback(data: FeedbackContainer, feedbackBefore: String) {
        feedback = feedback.copy(feedbackBefore = feedbackBefore)

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

    private fun generateRequestContent(question: String, userSentence: String): String {
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
       "nextQuestionEn": "<Generate a follow-up question based on the corrected sentence in English and before question : $question>",
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
                _uiState.value =
                    FeedbackUiState.PatchPronunciationFeedbacks(
                        round((it.audio.score.toDouble() / 5) * 100),
                        it.audio.recognized
                    )
            }.onFailure { _uiState.value = FeedbackUiState.Error(it.message.toString()) }
        }
    }

    fun updateBottomSheetMessages(type: RoleType, talk: String) {
        val updatedMessages = _bottomSheetMessage.value.toMutableList().apply {
            add(Message(type.name.lowercase(), talk))
        }
        _bottomSheetMessage.value = updatedMessages
    }

    fun resetTalk() {
        messages.clear()
        _bottomSheetMessage.value = emptyList()
    }

    companion object {
        const val GPT_SYSTEM =
            "You are a kind system that makes questions according to the level of user answers"
    }
}

sealed interface FeedbackUiState {
    data object Loading : FeedbackUiState

    data object Success : FeedbackUiState

    data object Empty : FeedbackUiState

    data class Error(val errorMessage: String) : FeedbackUiState

    data class PatchGptFeedbacks(val data: FeedbackContainer) : FeedbackUiState

    data class PatchPronunciationFeedbacks(val score: Double, val answer: String) : FeedbackUiState
}
