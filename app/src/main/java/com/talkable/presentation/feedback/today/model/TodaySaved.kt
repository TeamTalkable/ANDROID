package com.talkable.presentation.feedback.today.model

data class TodaySaved(
    val word: String = "",
    val sentence: String = "",
    val translation: String = "",
    val type: ItemType
)

enum class ItemType {
    WORD, SENTENCE
}