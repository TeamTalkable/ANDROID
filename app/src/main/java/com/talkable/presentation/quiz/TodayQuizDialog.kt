package com.talkable.presentation.quiz

import android.os.Bundle
import android.widget.LinearLayout.LayoutParams
import android.widget.Space
import androidx.fragment.app.FragmentManager
import coil.load
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.Key.TODAY_QUIZ_COUNT
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.core.util.fragment.dpToPx
import com.talkable.core.view.visible
import com.talkable.databinding.DialogTodayQuizBinding

class TodayQuizDialog : BindingDialogFragment<DialogTodayQuizBinding>(R.layout.dialog_today_quiz) {

    override fun initView() {
        initCancelBtnClickListener()
        initRetryBtnClickListener()
        val count = tag?.toInt() ?: 0
        setQuestion(count)
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 30f)
    }

    //TODO : 다이얼로그 여러개일 때
    private fun handleMultipleDialog() {
        val count = arguments?.getInt(TODAY_QUIZ_COUNT, 0) ?: 0
        if (count > 0) {
            addSpaces(3 - (tag?.toInt() ?: 0))
            binding.tvTodayQuizPage.visible(true)
            binding.tvTodayQuizPage.text = getString(R.string.label_quiz_app_bar_count, 1, count)
        }
    }

    private fun addSpaces(spaces: Int) {
        val parentLayout = binding.layoutRoot
        repeat(spaces) {
            val space = Space(parentLayout.context)
            val layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                27.dpToPx(parentLayout.context)
            )
            space.layoutParams = layoutParams
            parentLayout.addView(space)
        }
    }

    private fun initCancelBtnClickListener() {
        binding.ivTodayQuizCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initRetryBtnClickListener() = with(binding) {
        btnTodayQuizRetry.setOnClickListener {
            layoutCard.setBackgroundResource(R.drawable.shape_white_fill_12_rect)
            groupTodayQuizBad.visible(false)
            groupTodayQuizQuestion.visible(true)
        }
    }

    private fun setQuestion(tag: Int) = with(binding) {
        binding.tvTodayQuizQuestion.text =
            getString(R.string.label_today_quiz_question, mock[tag].first)
        val selectedList = mock[tag].second
        val otherOptions = mutableListOf<String>()
        for (i in mock.indices) {
            if (i != tag) {
                otherOptions.add(mock[i].second.random())
            }
        }

        val allOptions = (selectedList + otherOptions[0]).shuffled()

        val chips = listOf(
            cgQuizMeaningAnswer1, cgQuizMeaningAnswer2, cgQuizMeaningAnswer3, cgQuizMeaningAnswer4
        )

        chips.forEachIndexed { index, chip ->
            chip.text = allOptions[index]
            chip.setOnClickListener {
                groupTodayQuizQuestion.visible(false)
                if (allOptions[index] == otherOptions[0]) {
                    tvTodayQuizEnglish.text = mock[tag].first
                    tvTodayQuizKorean.text = mock[tag].third
                    binding.ivTodayQuiz.load("https://github.com/user-attachments/assets/b070adad-2b6e-4b0e-81ad-56c63c72f8da")
                    groupTodayQuizGood.visible(true)
                } else {
                    layoutCard.setBackgroundResource(R.drawable.shape_font5_fill_12_rect)
                    groupTodayQuizBad.visible(true)
                }
            }
        }
    }

    companion object {
        fun createNewInstance(count: Int, childFragmentManager: FragmentManager) = repeat(count) {
            TodayQuizDialog().apply {
                arguments = Bundle().apply {
                    putInt(TODAY_QUIZ_COUNT, count)
                }
            }.show(childFragmentManager, it.toString())
        }

        val mock = listOf(
            Triple("School", listOf("학교", "훈련시키다", "교육시키다"), "n. 학교\nv. 훈련시키다,  교육시키다"),
            Triple("Library", listOf("도서관", "서재", "장서"), "n. 도서관, 서재, 장서"),
            Triple(
                "Train", listOf("기차", "훈련하다", "연습하다"), "n. 기차\nv. 훈련하다, 연습하다"
            )
        )
    }
}