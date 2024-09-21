package com.talkable.presentation.quiz

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentQuizResultBinding
import kotlin.properties.Delegates

class QuizResultFragment :
    BindingFragment<FragmentQuizResultBinding>(R.layout.fragment_quiz_result) {

    private var type by Delegates.notNull<Int>()
    private var totalCount by Delegates.notNull<Int>()
    private var correctCount by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getInt(Key.QUIZ_KEY) ?: return
        totalCount = arguments?.getInt(Key.QUIZ_RESULT_TOTAL) ?: return
        correctCount = arguments?.getInt(Key.QUIZ_RESULT_CORRECT) ?: return
    }

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        setLayout()
        binding.btnQuizContinue.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setLayout() = with(binding) {
        when (type) {
            Quiz.SPELLING.title -> {
                groupQuizResultEtc.visible(true)
                setTitle("스펠링 완료!")
                setCountText()
            }

            Quiz.FLASH.title -> {
                groupQuizResultEtc.visible(true)
                setTitle("플래쉬 카드 완료!")
                setFlashTypeLabel()
                setCountText()
            }

            Quiz.AUTO.title -> {
                groupQuizResultAuto.visible(true)
                setTitle("AUTO 완료!")
                binding.tvQuizResultAutoCount.text = "${totalCount}개"
            }

            Quiz.MEANING.title -> {
                groupQuizResultEtc.visible(true)
                setTitle("뜻 맞추기 완료!")
                setCountText()
            }
        }
    }

    private fun setTitle(title: String) {
        binding.tvQuizResultTitle.text = title
    }

    private fun setCountText() {
        binding.tvQuizResultCorrectCount.text = "${totalCount - correctCount}개"
        binding.tvQuizResultDifficultCount.text = "${correctCount}개"
    }

    private fun setFlashTypeLabel() {
        binding.tvQuizResultCorrectLabel.text = "어려워요"
        binding.tvQuizResultDifficultLabel.text = "외웠어요"
    }
}