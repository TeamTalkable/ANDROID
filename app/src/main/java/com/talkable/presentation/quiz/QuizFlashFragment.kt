package com.talkable.presentation.quiz

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentQuizFlashBinding

class QuizFlashFragment : BindingFragment<FragmentQuizFlashBinding>(R.layout.fragment_quiz_flash) {

    private val quizViewModel: QuizViewModel by viewModels()

    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.layoutQuizFlashAppbar.count =
            getString(R.string.label_quiz_app_bar_count, 1, mock.size)
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
        val clickListener = View.OnClickListener {
            layoutQuizFlashCard.tvQuizAutoKorean.visible(false)
            layoutQuizFlashCard.ivQuizFlashShow.isSelected = false
            quizViewModel.setNextQuestion()
        }
        tvQuizFlashMemorized.setOnClickListener(clickListener)
        tvQuizFlashDifficult.setOnClickListener(clickListener)
    }

    private fun observeQuestionIndex() {
        quizViewModel.currentQuestionIndex.observe(this) { index ->
            if (index < mock.size) {
                updateNextFlashCard(mock[index])
                binding.layoutQuizFlashAppbar.count =
                    getString(R.string.label_quiz_app_bar_count, index + 1, mock.size)
            } else {
                navigateToBack()
            }
        }
    }

    private fun updateNextFlashCard(data: Pair<String, String>) = with(binding) {
        layoutQuizFlashCard.tvQuizAutoEnglish.text = data.first
        layoutQuizFlashCard.tvQuizAutoKorean.text = data.second
    }

    private fun navigateToBack() = findNavController().popBackStack()

    companion object {
        val mock = listOf(
            Pair("School", "n. 학교\nv. 훈련시키다, 교육시키다"),
            Pair("Book", "n. 책\nv. 예약하다"),
            Pair("Play", "n. 연극\nv. 놀다, 연주하다"),
            Pair("Watch", "n. 시계\nv. 지켜보다"),
            Pair("Plant", "n. 식물\nv. 심다")
        )
    }
}