package com.talkable.presentation.quiz

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentQuizFlashBinding

class QuizFlashFragment : BindingFragment<FragmentQuizFlashBinding>(R.layout.fragment_quiz_flash) {

    private val quizViewModel: QuizViewModel by viewModels()
    private var correctCount = 0

    override fun initView() {
        hideCoachMark()
        binding.layoutQuizFlashAppbar.count =
            getString(R.string.label_quiz_app_bar_count, 1, mockLong.size)
        initBackNavigationIconClickListener()
        initFlashLearnStatusLabelClickListener()
        observeQuestionIndex()
        test()
    }

    private fun hideCoachMark() {
        binding.ivFlashCoach.setOnClickListener {
            binding.groupFlashCoachMark.visible(false)
        }
    }

    @SuppressLint("ResourceType")
    private fun test() = with(binding) {
        var isFront = true
        val scale: Float = root.context.resources.displayMetrics.density
        layoutFlashBack.cameraDistance = 8000 * scale
        layoutFlashFront.cameraDistance = 8000 * scale

        val frontAnim = AnimatorInflater.loadAnimator(root.context, R.anim.front_animator)
        val backAnim = AnimatorInflater.loadAnimator(root.context, R.anim.back_animator)

        ivFlashImg.setOnClickListener {
            if (isFront) {
                frontAnim.setTarget(layoutFlashFront)
                backAnim.setTarget(layoutFlashBack)
                frontAnim.start()
                backAnim.start()
                isFront = false
            } else {
                frontAnim.setTarget(layoutFlashBack)
                backAnim.setTarget(layoutFlashFront)
                frontAnim.start()
                backAnim.start()
                isFront = true
            }
        }
    }

    private fun initBackNavigationIconClickListener() {
        binding.layoutQuizFlashAppbar.toolbarQuiz.setNavigationOnClickListener {
            navigateToBack()
        }
    }

    private fun initFlashLearnStatusLabelClickListener() = with(binding) {
        fun onFlashCardClick() {
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
                if (index == 1) binding.ivFlashImg.setImageResource(R.drawable.img_flash_book)
            } else {
                navigateToResult(mockLong.size)
            }
        }
    }

    private fun updateNextFlashCard(data: Pair<String, String>) = with(binding) {
        tvQuizEn.text = data.first
        tvQuizKo.text = data.second
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