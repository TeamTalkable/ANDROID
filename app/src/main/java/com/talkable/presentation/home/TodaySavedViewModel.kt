package com.talkable.presentation.home

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.talkable.data.FirebaseFactory
import com.talkable.presentation.home.model.Saved
import com.talkable.presentation.home.model.TalkSavedModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodaySavedViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<TodaySavedUiState>(TodaySavedUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getTodaySaved()
    }

    private fun getTodaySaved() {
        FirebaseFactory.savedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val savedWordList = getSavedWordList(snapshot)
                val savedSentenceList = getSavedSentenceList(snapshot)

                val talkSavedModel = TalkSavedModel(
                    savedWordList = savedWordList,
                    savedSentenceList = savedSentenceList
                )

                _uiState.value = TodaySavedUiState.Success(talkSavedModel)
            }

            override fun onCancelled(error: DatabaseError) {
                _uiState.value = TodaySavedUiState.Error(error.message)
            }
        })
    }

    private fun getSavedWordList(snapshot: DataSnapshot): MutableList<Saved.Word> {
        val savedWordList = mutableListOf<Saved.Word>()
        snapshot.child("savedWordList").children.forEach { dataSnapshot ->
            dataSnapshot.getValue(Saved.Word::class.java)?.let { savedWordList.add(it) }
        }
        return savedWordList
    }

    private fun getSavedSentenceList(snapshot: DataSnapshot): MutableList<Saved.Sentence> {
        val savedSentenceList = mutableListOf<Saved.Sentence>()
        snapshot.child("savedSentenceList").children.forEach { dataSnapshot ->
            dataSnapshot.getValue(Saved.Sentence::class.java)?.let { savedSentenceList.add(it) }
        }
        return savedSentenceList
    }
}

sealed interface TodaySavedUiState {
    data object Loading : TodaySavedUiState
    data class Success(val data: TalkSavedModel) : TodaySavedUiState
    data class Error(val errorMessage: String) : TodaySavedUiState
}