package com.talkable.presentation.talk.feedback.model

import com.talkable.presentation.feedback.today.model.TodayFeedback
import com.talkable.presentation.feedback.today.model.TodayFeedbackModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class TalkFeedbackModel(
    val talkFeedbackId: Int = 0,
    val talkTime: String = "",
    val talkDate: String = "",
    val flowerImage: String = "",
    val remainTime: String = "",
    val feedbackBefore: String = "",
    val learnedExpression: MutableList<Learned.Expression> = mutableListOf(),
    val learnedGrammar: MutableList<Learned.Grammar> = mutableListOf(),
    val learnedPronunciation: MutableList<Learned.Pronunciation> = mutableListOf(),
    val learnedAfterAnswer: MutableList<Learned.AfterAnswer> = mutableListOf(),
    val isGraphChanged: Boolean = false,
)

@Serializable
sealed class Learned {
    @SerialName("category")
    abstract val type: String

    @Serializable
    @SerialName("Label")
    data class Label(
        @SerialName("category")
        override val type: String = "",
    ) : Learned()

    @Serializable
    @SerialName("Expression")
    data class Expression(
        @SerialName("category")
        override val type: String = "",
        @SerialName("wordEnglish")
        val wordEnglish: String = "",
        @SerialName("wordKorean")
        val wordKorean: String = "",
        @SerialName("expressionAfterAnswer")
        val expressionAfterAnswer: AfterAnswer = AfterAnswer(),
    ) : Learned()

    @Serializable
    @SerialName("Grammar")
    data class Grammar(
        @SerialName("category")
        override val type: String = "",
        @SerialName("wrongGrammar")
        val wrongGrammar: String = "",
        @SerialName("correctGrammar")
        val correctGrammar: String = "",
        @SerialName("reason")
        val reason: String = "",
        @SerialName("grammarAfterAnswer")
        val grammarAfterAnswer: AfterAnswer = AfterAnswer(),
    ) : Learned()

    @Serializable
    @SerialName("Pronunciation")
    data class Pronunciation(
        @SerialName("category")
        override val type: String = "",
        val englishWord: String = "",
        val koreanWord: String = "",
        val pronunciationEnglish: String = "",
        val wordAccuracy: String? = null,
        var isSelected: Boolean = false,
    ) : Learned()

    @Serializable
    @SerialName("AfterAnswer")
    data class AfterAnswer(
        @SerialName("category")
        override val type: String = "",
        val afterFullAnswer: String = "",
        val afterAnswerParts: List<String> = emptyList(),
    ) : Learned()
}

fun TalkFeedbackModel.toTodayFeedback(): TodayFeedbackModel {
    return TodayFeedbackModel(
        todayFeedbackId = this.talkFeedbackId,

        todayExpression = this.learnedExpression.map {
            TodayFeedback.Expression(
                type = it.type,
                english = it.expressionAfterAnswer.afterFullAnswer,
                translation = it.wordKorean,
                feedbackBefore = it.wordEnglish,
                feedbackAfter = it.expressionAfterAnswer.afterFullAnswer
            )
        },

        todayGrammar = this.learnedGrammar.map {
            TodayFeedback.Grammar(
                type = it.type,
                wrong = it.wrongGrammar,
                correct = it.correctGrammar,
                reason = it.reason,
                feedbackBefore = this.feedbackBefore,
                feedbackAfter = it.grammarAfterAnswer.afterFullAnswer
            )
        },

        todayPronunciation = this.learnedPronunciation.map {
            TodayFeedback.Pronunciation(
                type = it.type,
                word = it.englishWord,
                pronunciation = it.pronunciationEnglish,
                translation = it.koreanWord,
                sentence = this.feedbackBefore,
                accuracy = it.wordAccuracy?.toInt() ?: 0
            )
        }
    )
}