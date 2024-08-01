package com.talkable.presentation.quiz

import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentQuizSpellingMeaningBinding

class QuizSpellingMeaningFragment :
    BindingFragment<FragmentQuizSpellingMeaningBinding>(R.layout.fragment_quiz_spelling_meaning) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        val type = arguments?.getInt(Key.QUIZ_KEY)

        binding.type = if (type == Quiz.SPELLING.title) Quiz.SPELLING else Quiz.MEANING
    }
}