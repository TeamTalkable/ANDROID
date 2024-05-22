package com.talkable.presentation.talk.feedback.mapper

import com.talkable.presentation.talk.feedback.model.Learned
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

fun TalkFeedbackModel.toLearnedList(): List<Learned> {
    val learnedList = mutableListOf<Learned>()
    learnedList.add(Learned.Label(learnedExpression.first().type))
    learnedExpression.forEach { learnedList.add(it) }
    learnedList.add(Learned.Label(learnedGrammar.first().type))
    learnedGrammar.forEach { learnedList.add(it) }
    learnedList.add(Learned.Label(learnedPronunciation.first().type))
    learnedPronunciation.forEach { learnedList.add(it) }

    return learnedList
}