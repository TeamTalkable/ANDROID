package com.talkable.presentation.mypage.saved.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedWordViewModel : ViewModel() {

    private val _savedWords = MutableLiveData<SavedListModel>()
    val savedWords: LiveData<SavedListModel> get() = _savedWords

    init {
        _savedWords.value = SavedListModel(
            savedWordId = 1,
            savedWordList = listOf(
                SavedWord(type = 0, word = "justice", translation = "정의", tag = "어려워요"),
                SavedWord(type = 1, word = "library", translation = "도서관", tag = "외웠어요"),
                SavedWord(type = 2, word = "transparent", translation = "투명한", tag = "암기 중")
            )
        )
    }

    fun updateSavedWords(newList: List<SavedWord>) {
        _savedWords.value = SavedListModel(savedWordId = _savedWords.value?.savedWordId ?: 1, savedWordList = newList)
    }
}