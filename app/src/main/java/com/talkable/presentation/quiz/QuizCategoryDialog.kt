package com.talkable.presentation.quiz

import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.DialogQuizCategoryBinding

class QuizCategoryDialog :
    BindingDialogFragment<DialogQuizCategoryBinding>(R.layout.dialog_quiz_category) {
    override fun initView() {

    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 48.0f)
    }

    companion object {
        fun createNewInstance() = QuizCategoryDialog()
    }
}