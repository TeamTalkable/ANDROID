package com.talkable.presentation.talk.feedback

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.talkable.data.FirebaseFactory
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class FinalTalkFeedbackViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FinalFeedbackUiState>(FinalFeedbackUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        patchFinalFeedback()
    }

    private fun patchFinalFeedback() {
        FirebaseFactory.feedbackRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(TalkFeedbackModel::class.java) ?: return
                Timber.e(value.toString())
                _uiState.value = FinalFeedbackUiState.Success(value)
            }

            override fun onCancelled(error: DatabaseError) {
                _uiState.value = FinalFeedbackUiState.Error(error.message)
            }
        })
    }
}

sealed interface FinalFeedbackUiState {
    data object Loading : FinalFeedbackUiState

    data class Success(val data: TalkFeedbackModel) : FinalFeedbackUiState

    data class Error(val errorMessage: String) : FinalFeedbackUiState
}
