package com.talkable.presentation.review.model

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.talkable.data.FirebaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodaySavedViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<TodaySavedUiState>(TodaySavedUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var savedWordList: MutableList<Saved.Word> = mutableListOf()
    private var savedSentenceList: MutableList<Saved.Sentence> = mutableListOf()

    init {
        getTodaySaved()
    }

    private fun getTodaySaved() {
        val savedWordQuery: Query =
            FirebaseFactory.savedRef.child("savedWordList").orderByChild("timestamp")
        val savedSentenceQuery: Query =
            FirebaseFactory.savedRef.child("savedSentenceList").orderByChild("timestamp")

        savedWordQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                savedWordList = getSavedWordList(snapshot)
                savedWordList.sortByDescending { it.timestamp }
                updateUiState()
            }

            override fun onCancelled(error: DatabaseError) {
                _uiState.value = TodaySavedUiState.Error(error.message)
            }
        })

        savedSentenceQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                savedSentenceList = getSavedSentenceList(snapshot)
                savedSentenceList.sortByDescending { it.timestamp }
                updateUiState()
            }

            override fun onCancelled(error: DatabaseError) {
                _uiState.value = TodaySavedUiState.Error(error.message)
            }
        })
    }

    private fun updateUiState() {
        if (savedWordList.isNotEmpty() || savedSentenceList.isNotEmpty()) {
            val talkSavedModel = TalkSavedModel(
                savedWordList = savedWordList,
                savedSentenceList = savedSentenceList
            )
            _uiState.value = TodaySavedUiState.Success(talkSavedModel)
        }
    }

    private fun getSavedWordList(snapshot: DataSnapshot): MutableList<Saved.Word> {
        val savedWordList = mutableListOf<Saved.Word>()
        snapshot.children.forEach { dataSnapshot ->
            dataSnapshot.getValue(Saved.Word::class.java)?.let { savedWordList.add(it) }
        }
        return savedWordList
    }

    private fun getSavedSentenceList(snapshot: DataSnapshot): MutableList<Saved.Sentence> {
        val savedSentenceList = mutableListOf<Saved.Sentence>()
        snapshot.children.forEach { dataSnapshot ->
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