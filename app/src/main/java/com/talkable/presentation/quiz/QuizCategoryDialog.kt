package com.talkable.presentation.quiz

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.Key
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.DialogQuizCategoryBinding

class QuizCategoryDialog :
    BindingDialogFragment<DialogQuizCategoryBinding>(R.layout.dialog_quiz_category) {
    override fun initView() {
        initSpellingClickListener()
        initMeaningClickListener()
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 48.0f)
    }

    private fun initSpellingClickListener() {
        binding.layoutQuizCategorySpelling.layoutQuizDialog.setOnClickListener {
            dismiss()
            navigateToSpellingMeaningFragment(Quiz.SPELLING.title)
        }
    }

    private fun initMeaningClickListener() {
        binding.layoutQuizCategoryMeaning.layoutQuizDialog.setOnClickListener {
            dismiss()
            navigateToSpellingMeaningFragment(Quiz.MEANING.title)
        }
    }

    private fun navigateToSpellingMeaningFragment(type: Int) = findNavController().navigate(
        R.id.action_saved_to_quiz_spelling_meaning, bundleOf(Key.QUIZ_KEY to type)
    )

    companion object {
        fun createNewInstance() = QuizCategoryDialog()
    }
}