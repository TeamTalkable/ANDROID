package com.talkable.presentation.quiz

import android.os.Handler
import android.os.Looper
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.BottomSheetKey.QUIZ_AUTO_SPEED
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.view.setOnDuplicateBlockClick
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentQuizAutoBinding
import com.talkable.presentation.quiz.QuizFlashFragment.Companion.mockLong

//TODO:나중에 REFATOR
class QuizAutoFragment : BindingFragment<FragmentQuizAutoBinding>(R.layout.fragment_quiz_auto) {

    private val quizViewModel: QuizViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var isAuto = true

    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.layoutQuizAutoAppbar.count =
            getString(R.string.label_quiz_app_bar_count, 1, mockLong.size)
        initBackPressCallback()
        setRunnable()
        setFlashAutoPlay()
        initBackNavigationIconClickListener()
        initStopFlashAutoBtnClickListener()
        initNextFlashBtnClickListener()
        observeQuestionIndex()
        initSetTimeBtnClickListener()
    }

    override fun onPause() {
        super.onPause()
        blockFlashAutoHandleCallback()
    }

    override fun onDestroyView() {
        blockFlashAutoHandleCallback()
        super.onDestroyView()
    }

    private fun initBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            blockFlashAutoHandleCallback()
        }
    }

    private fun setRunnable() {
        runnable = object : Runnable {
            override fun run() {
                if (isAuto) {
                    quizViewModel.setNextQuestion()
                    handler.postDelayed({
                        handleAnswerViewVisible(true)
                        handler.postDelayed(this, 500)
                    }, 1000)
                }
            }
        }
    }

    private fun setFlashAutoPlay() {
        binding.ivQuizAutoStop.isSelected = false
        handler.postDelayed({
            handleAnswerViewVisible(true)
            handler.postDelayed(runnable, 500)
        }, 1000)
    }

    private fun handleAnswerViewVisible(isVisible: Boolean) {
        binding.layoutQuizAutoCard.tvQuizAutoKorean.visible(isVisible)
        binding.layoutQuizAutoCard.ivQuizAuto.visible(isVisible)
    }

    private fun initBackNavigationIconClickListener() {
        binding.layoutQuizAutoAppbar.toolbarQuiz.setNavigationOnClickListener {
            navigateToBack()
        }
    }

    private fun initStopFlashAutoBtnClickListener() = with(binding) {
        ivQuizAutoStop.setOnClickListener {
            val isSelected = ivQuizAutoStop.isSelected
            if (isSelected) {
                isAuto = true
                setFlashAutoPlay()
            } else {
                isAuto = false
                handler.removeCallbacks(runnable)
            }
            ivQuizAutoStop.isSelected = !isSelected
        }
    }

    private fun initNextFlashBtnClickListener() {
        binding.ivQuizAutoNext.setOnDuplicateBlockClick {
            if (isAuto) {
                blockFlashAutoHandleCallback()
                quizViewModel.setNextQuestion()
            } else {
                isAuto = true
                setFlashAutoPlay()
            }
        }
    }

    private fun observeQuestionIndex() {
        quizViewModel.currentQuestionIndex.observe(this) { index ->
            if (index < mockLong.size) {
                updateNextFlashCard(mockLong[index])
                handleAnswerViewVisible(false)
                binding.layoutQuizAutoAppbar.count =
                    getString(R.string.label_quiz_app_bar_count, index + 1, mockLong.size)
            } else {
                blockFlashAutoHandleCallback()
                binding.ivQuizAutoStop.isEnabled = false
                binding.ivQuizAutoNext.setOnClickListener {
                    navigateToBack()
                }
            }
        }
    }

    private fun blockFlashAutoHandleCallback() {
        binding.ivQuizAutoStop.isSelected = true
        isAuto = false
        handler.removeCallbacks(runnable)
    }

    private fun updateNextFlashCard(data: Pair<String, String>) = with(binding) {
        layoutQuizAutoCard.tvQuizAutoEnglish.text = data.first
        layoutQuizAutoCard.tvQuizAutoKorean.text = data.second
    }

    private fun navigateToBack() = findNavController().popBackStack()

    private fun initSetTimeBtnClickListener() {
        binding.ivQuizAutoTime.setOnClickListener {
            blockFlashAutoHandleCallback()
            initSetTimeBottomSheet()
        }
    }

    private fun initSetTimeBottomSheet() {
        QuizAutoSpeedBottomSheet().show(childFragmentManager, QUIZ_AUTO_SPEED)
    }
}