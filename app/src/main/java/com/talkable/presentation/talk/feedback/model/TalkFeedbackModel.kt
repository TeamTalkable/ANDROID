package com.talkable.presentation.talk.feedback.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class TalkFeedbackModel(
    val talkFeedbackId: Int = 0,
    val talkTime: String = "",
    val flowerImage: String = "",
    val remainTime: String = "",
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