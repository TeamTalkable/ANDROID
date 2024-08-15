package com.talkable.presentation.feedback.today.model

data class TodaySavedModel(
    val todaySavedId: Int,
    val todaySavedList: List<TodaySaved>
)

data class TodaySaved(
    val word: String,
    val translation: String?,
    val verb: String?,
    val noun: String?,
)