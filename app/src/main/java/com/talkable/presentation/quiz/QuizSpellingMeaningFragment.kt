package com.talkable.presentation.quiz

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.DialogKey
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentQuizSpellingMeaningBinding

class QuizSpellingMeaningFragment :
    BindingFragment<FragmentQuizSpellingMeaningBinding>(R.layout.fragment_quiz_spelling_meaning) {

    private val quizViewModel: QuizViewModel by viewModels()
    val type = arguments?.getInt(Key.QUIZ_KEY)

    override fun initView() {
        statusBarColorOf(R.color.white)
        initBackNavigationIconClickListener()
        binding.layoutQuizSpellingMeaningAppbar.count =
            getString(R.string.label_quiz_app_bar_count, 1, mock.size)
        binding.type = if (type == Quiz.SPELLING.title) Quiz.SPELLING else Quiz.MEANING
        initMeaningLayout()
    }

    private fun initBackNavigationIconClickListener() {
        binding.layoutQuizSpellingMeaningAppbar.toolbarQuiz.setNavigationOnClickListener {
            navigateToBack()
        }
    }

    private fun initMeaningLayout() {
        setQuizWordText(mock[0].first)
        updateMeaningQuestion(mock[0])
        observeQuestionIndex()
    }

    private fun setQuizWordText(text: String) {
        binding.tvQuizSpellingMeaningWord.text = text
    }

    private fun updateMeaningQuestion(question: Pair<String, String>) {
        val (question, answer) = question
        val otherOptions = mock.filter { it.first != question }.map { it.second }.shuffled().take(3)
        val allOptions = (otherOptions + answer).shuffled()

        val chips = listOf(
            binding.cgQuizMeaningAnswer1,
            binding.cgQuizMeaningAnswer2,
            binding.cgQuizMeaningAnswer3,
            binding.cgQuizMeaningAnswer4
        )

        chips.forEachIndexed { index, chip ->
            chip.text = allOptions[index]
            chip.setOnClickListener {
                if (allOptions[index] == answer) {
                    quizViewModel.setNextQuestion()
                } else {
                    QuizErrorDialog.createNewInstance().show(
                        childFragmentManager, DialogKey.QUIZ_ERROR_DIALOG
                    )
                }
            }
        }
    }

    private fun observeQuestionIndex() {
        quizViewModel.currentQuestionIndex.observe(this) { index ->
            if (index < mock.size) {
                setQuizWordText(mock[index].first)
                updateMeaningQuestion(mock[index])
                binding.layoutQuizSpellingMeaningAppbar.count =
                    getString(R.string.label_quiz_app_bar_count, index + 1, mock.size)
            } else {
                navigateToBack()
            }
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    companion object {
        val mock = listOf(
            Pair("School", "학교"),
            Pair("Library", "도서관"),
            Pair("Hospital", "병원"),
            Pair("Airport", "공항"),
            Pair("Restaurant", "식당")
        )
    }
}