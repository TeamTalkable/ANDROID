package com.talkable.presentation.talk.feedback.model

data class TalkFeedbackModel(
    val talkFeedbackId: Int,
    val talkTime: String,
    val flowerImage: String,
    val remainTime: String,
    val learnedExpression: List<Learned.Expression>,
    val learnedGrammar: List<Learned.Grammar>,
    val learnedPronunciation: List<Learned.Pronunciation>,
    val isGraphChanged: Boolean,
)

sealed class Learned {
    abstract val type: String

    data class Label(
        override val type: String,
    ) : Learned()

    data class Expression(
        override val type: String,
        val wordEnglish: String,
        val wordKorean: String,
    ) : Learned()

    data class Grammar(
        override val type: String,
        val wrongGrammar: String,
        val correctGrammar: String,
        val reason: String,
    ) : Learned()

    data class Pronunciation(
        override val type: String,
        val englishWord: String,
        val koreanWord: String,
        val pronunciationEnglish: String,
    ) : Learned()
}


