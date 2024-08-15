package com.talkable.presentation.feedback.today

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentTodayFeedbackBinding
import com.talkable.presentation.feedback.today.model.TodayFeedback
import com.talkable.presentation.feedback.today.model.TodayFeedbackModel
import com.talkable.presentation.mypage.saved.Constants


class TodayFeedbackFragment :
    BindingFragment<FragmentTodayFeedbackBinding>(R.layout.fragment_today_feedback) {

    private lateinit var todaySavedAdapter: TodayFeedbackAdapter

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initTodayFeedbackAdapter()
        initFeedbackChipClickListener()
    }

    private fun initTodayFeedbackAdapter() {
        todaySavedAdapter = TodayFeedbackAdapter(requireContext())
        with(binding.rvSavedWord) {
            adapter = todaySavedAdapter
            layoutManager = LinearLayoutManager(context)
        }
        todaySavedAdapter.submitList(combineAllFeedbackData())
    }

    private fun initFeedbackChipClickListener() {
        binding.cgTodayFeedbackList.setOnCheckedStateChangeListener { chipGroup, _ ->
            val newData = when (chipGroup.checkedChipId) {
                Constants.NO_CHIP_SELECTED -> combineAllFeedbackData()
                R.id.chip_today_expression -> todayFeedbackMockData.todayExpression
                R.id.chip_today_grammar -> todayFeedbackMockData.todayGrammar
                R.id.chip_today_pronunciation -> todayFeedbackMockData.todayPronunciation
                else -> emptyList()
            }
            todaySavedAdapter.submitList(newData)
        }
    }

    private fun combineAllFeedbackData(): List<TodayFeedback> {
        return todayFeedbackMockData.todayExpression +
                todayFeedbackMockData.todayGrammar +
                todayFeedbackMockData.todayPronunciation
    }

    companion object {
        fun newInstance(category: TodaySavedCategory): TodayFeedbackFragment {
            return TodayFeedbackFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Constants.KEY_CATEGORY, category)
                }
            }
        }

        val todayFeedbackMockData = TodayFeedbackModel(
            todayFeedbackId = 1,
            todayExpression = listOf(
                TodayFeedback.Expression(
                    type = "Expression",
                    english = "I like apples.",
                    translation = "나는 사과를 좋아해.",
                    feedbackBefore = "I like apple.",
                    feedbackAfter = "I like apples."
                ),
                TodayFeedback.Expression(
                    type = "Expression",
                    english = "He runs fast.",
                    translation = "그는 빠르게 달려.",
                    feedbackBefore = "He run fast.",
                    feedbackAfter = "He runs fast."
                )
            ),
            todayGrammar = listOf(
                TodayFeedback.Grammar(
                    type = "Grammar",
                    wrong = "go",
                    correct = "went",
                    reason = "The correct past tense of 'go' is 'went'.",
                    feedbackBefore = "I go to the store.",
                    feedbackAfter = "I went to the store."
                ),
                TodayFeedback.Grammar(
                    type = "Grammar",
                    wrong = "don't",
                    correct = "doesn't",
                    reason = "Use 'doesn't' with 'she' in negative sentences.",
                    feedbackBefore = "She don't like it.",
                    feedbackAfter = "She doesn't like it."
                )
            ),
            todayPronunciation = listOf(
                TodayFeedback.Pronunciation(
                    type = "Pronunciation",
                    word = "read",
                    pronunciation = "riːd",
                    translation = "읽다",
                    sentence = "He is learning how to read and write in English",
                    accuracy = 75
                )
            )
        )
    }
}