package com.talkable.presentation.mypage.saved.model

data class SavedListModel(
    val savedWordId: Int,
    val savedWordList: List<SavedWord>
)

data class SavedWord(
    val type: Int,
    val word: String,
    val translation: String,
    val tag: String,
)