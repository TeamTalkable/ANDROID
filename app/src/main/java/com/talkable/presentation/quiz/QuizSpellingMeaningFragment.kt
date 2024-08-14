package com.talkable.presentation.quiz

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.DialogKey
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.view.setOnDuplicateBlockClick
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentQuizSpellingMeaningBinding
import timber.log.Timber

class QuizSpellingMeaningFragment :
    BindingFragment<FragmentQuizSpellingMeaningBinding>(R.layout.fragment_quiz_spelling_meaning) {

    private val quizViewModel: QuizViewModel by viewModels()
    private var spellingIndex = 0

    override fun initView() {
        val type = arguments?.getInt(Key.QUIZ_KEY)
        statusBarColorOf(R.color.white)
        initBackNavigationIconClickListener()
        binding.layoutQuizSpellingMeaningAppbar.count =
            getString(R.string.label_quiz_app_bar_count, 1, mock.size)
        Timber.d(type.toString())
        binding.type = if (type == Quiz.SPELLING.title) Quiz.SPELLING else Quiz.MEANING
        observeQuestionIndex(type)
        initMeaningLayout()
        initCompleteBtnClickListener()
    }

    private fun initBackNavigationIconClickListener() {
        binding.layoutQuizSpellingMeaningAppbar.toolbarQuiz.setNavigationOnClickListener {
            navigateToBack()
        }
    }

    private fun initMeaningLayout() {
        setQuizWordText(mock[0].first)
        updateMeaningQuestion(mock[0])
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
                    showQuizErrorDialog()
                }
            }
        }
    }

    private fun showQuizErrorDialog() {
        QuizErrorDialog.createNewInstance().show(
            childFragmentManager, DialogKey.QUIZ_ERROR_DIALOG
        )
    }

    private fun observeQuestionIndex(type: Int?) {
        var question = ""
        quizViewModel.currentQuestionIndex.observe(this) { index ->
            spellingIndex = index
            if (index < mock.size) {
                question =
                    if (type == Quiz.SPELLING.title) mock[index].second else mock[index].first
                setQuizWordText(question)
                updateMeaningQuestion(mock[index])
                binding.layoutQuizSpellingMeaningAppbar.count =
                    getString(R.string.label_quiz_app_bar_count, index + 1, mock.size)
            } else {
                navigateToBack()
            }
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    private fun initCompleteBtnClickListener() {
        binding.btnQuizSpellingMeaningCompelte.setOnDuplicateBlockClick {
            if (binding.etQuizSpellingUserAnswer.text.toString().equals(mock[spellingIndex].first, ignoreCase = true))
            {
                binding.tvQuizSpellingAnswer.visible(false)
                quizViewModel.setNextQuestion()
            } else {
                showQuizErrorDialog()
                binding.tvQuizSpellingAnswer.text = mock[spellingIndex].first
                binding.tvQuizSpellingMeaningCommand.text = "단어를 따라 적어보세요"
                binding.tvQuizSpellingAnswer.visible(true)
            }
        }
    }

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