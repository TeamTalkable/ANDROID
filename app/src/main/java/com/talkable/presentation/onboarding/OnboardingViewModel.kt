package com.talkable.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {

    private val _dialogClosed = MutableLiveData<Boolean>()
    val dialogClosed: LiveData<Boolean> get() = _dialogClosed

    fun closeDialog() {
        _dialogClosed.value = true
    }

    fun resetDialogClose() {
        _dialogClosed.value = false
    }
}