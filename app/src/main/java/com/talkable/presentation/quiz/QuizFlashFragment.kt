package com.talkable.presentation.quiz

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentQuizFlashBinding

class QuizFlashFragment : BindingFragment<FragmentQuizFlashBinding>(R.layout.fragment_quiz_flash) {

    private val quizViewModel: QuizViewModel by viewModels()
    private var correctCount = 0

    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.layoutQuizFlashCard.ivQuizAuto.load("https://github.com/user-attachments/assets/b070adad-2b6e-4b0e-81ad-56c63c72f8da")
        binding.layoutQuizFlashAppbar.count =
            getString(R.string.label_quiz_app_bar_count, 1, mockLong.size)
        initBackNavigationIconClickListener()
        initKoreanShowBtnClickEvent()
        initFlashLearnStatusLabelClickListener()
        observeQuestionIndex()
    }

    private fun initBackNavigationIconClickListener() {
        binding.layoutQuizFlashAppbar.toolbarQuiz.setNavigationOnClickListener {
            navigateToBack()
        }
    }

    private fun initKoreanShowBtnClickEvent() = with(binding.layoutQuizFlashCard) {
        ivQuizFlashShow.setOnClickListener {
            val isSelected = ivQuizFlashShow.isSelected
            ivQuizFlashShow.isSelected = !isSelected
            tvQuizAutoKorean.visible(!isSelected)
        }
    }

    private fun initFlashLearnStatusLabelClickListener() = with(binding) {
        fun onFlashCardClick() {
            layoutQuizFlashCard.tvQuizAutoKorean.visible(false)
            layoutQuizFlashCard.ivQuizFlashShow.isSelected = false
            quizViewModel.setNextQuestion()
        }

        tvQuizFlashMemorized.setOnClickListener {
            correctCount++;
            onFlashCardClick()
        }

        tvQuizFlashDifficult.setOnClickListener {
            onFlashCardClick()
        }
    }

    private fun observeQuestionIndex() {
        quizViewModel.currentQuestionIndex.observe(this) { index ->
            if (index < mockLong.size) {
                updateNextFlashCard(mockLong[index])
                binding.layoutQuizFlashAppbar.count =
                    getString(R.string.label_quiz_app_bar_count, index + 1, mockLong.size)
            } else {
                navigateToResult(mockLong.size)
            }
        }
    }

    private fun navigateToResult(totalCount: Int) =
        findNavController().navigate(
            R.id.action_quiz_flash_to_quiz_result,
            bundleOf(
                Key.QUIZ_KEY to Quiz.FLASH.title,
                Key.QUIZ_RESULT_TOTAL to totalCount,
                Key.QUIZ_RESULT_CORRECT to correctCount,
            )
        )

    private fun updateNextFlashCard(data: Pair<String, String>) = with(binding) {
        layoutQuizFlashCard.tvQuizAutoEnglish.text = data.first
        layoutQuizFlashCard.tvQuizAutoKorean.text = data.second
    }

    private fun navigateToBack() = findNavController().popBackStack()

    companion object {
        val mockLong = listOf(
            Pair("School", "n. 학교\nv. 훈련시키다, 교육시키다"),
            Pair("Book", "n. 책\nv. 예약하다"),
            Pair("Play", "n. 연극\nv. 놀다, 연주하다"),
            Pair("Watch", "n. 시계\nv. 지켜보다"),
            Pair("Plant", "n. 식물\nv. 심다")
        )
    }
}