package com.talkable.presentation.feedback.model

import com.talkable.presentation.talk.feedback.model.Learned

data class FeedbackExpressionModel(
    val feedbackId: Int,
    val question: String,
    val beforeAnswer: String,
    val afterFullAnswer: String,
    val afterAnswerParts: List<String>,
    val learnedExpression: List<Learned>,
)

data class FeedbackGrammarModel(
    val feedbackId: Int,
    val question: String,
    val beforeAnswer: String,
    val afterFullAnswer: String,
    val afterAnswerParts: List<String>,
    val learnedGrammar: List<Learned>,
)

data class FeedbackPronunciationModel(
    val feedbackId: Int,
    val accuracy: String,
    val fullAnswer: String,
    val answerParts: List<String>,
    val korean: String,
    val learnedPronunciation: List<Learned.Pronunciation>,
)
