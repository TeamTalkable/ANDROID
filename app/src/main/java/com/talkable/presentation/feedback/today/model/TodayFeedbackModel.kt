package com.talkable.presentation.feedback.today.model

data class TodayFeedbackModel(
    val todayFeedbackId: Int,
    val todayExpression: List<TodayFeedback.Expression>,
    val todayGrammar: List<TodayFeedback.Grammar>,
    val todayPronunciation: List<TodayFeedback.Pronunciation>,
)

sealed class TodayFeedback {
    abstract val type: String
    data class Expression(
        override val type: String,
        val english: String,
        val translation: String,
        val feedbackBefore: String,
        val feedbackAfter: String,
    ) : TodayFeedback()

    data class Grammar(
        override val type: String,
        val wrong: String,
        val correct: String,
        val reason: String,
        val feedbackBefore: String,
        val feedbackAfter: String,
    ) : TodayFeedback()

    data class Pronunciation(
        override val type: String,
        val word: String,
        val pronunciation: String,
        val translation: String,
        val sentence: String,
        val accuracy: Int
    ) : TodayFeedback()
}