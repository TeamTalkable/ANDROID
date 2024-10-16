package com.talkable.presentation.talk.feedback.mapper

import com.talkable.presentation.talk.feedback.model.Learned
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

fun TalkFeedbackModel.toLearnedList(): List<Learned> {
    val learnedList = mutableListOf<Learned>()
    learnedList.add(Learned.Label("학습한 표현"))
    learnedExpression.forEach {
        learnedList.add(it)
        if (!learnedList.contains(it.expressionAfterAnswer))
            learnedList.add(it.expressionAfterAnswer)
    }
    learnedList.add(Learned.Label("학습한 문법"))
    learnedGrammar.forEach {
        learnedList.add(it)
        if (!learnedList.contains(it.grammarAfterAnswer))
            learnedList.add(it.grammarAfterAnswer)
    }
    learnedList.add(Learned.Label("학습한 발음"))
    learnedPronunciation.forEach { learnedList.add(it) }

    return learnedList
}