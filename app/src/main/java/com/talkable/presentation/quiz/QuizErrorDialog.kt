package com.talkable.presentation.quiz

import android.os.Handler
import android.os.Looper
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.DialogSpellingMeaningErrorBinding

class QuizErrorDialog :
    BindingDialogFragment<DialogSpellingMeaningErrorBinding>(R.layout.dialog_spelling_meaning_error) {
    override fun initView() {
        handleDestroy()
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 56.0f)
    }

    private fun handleDestroy() {
        Handler(Looper.getMainLooper()).postDelayed({
            dismiss()
        }, 2000)
    }

    companion object {
        fun createNewInstance() = QuizErrorDialog()
    }
}