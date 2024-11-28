package com.talkable.presentation.feedback.today

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentTodayFeedbackBinding
import com.talkable.presentation.feedback.FeedbackViewModel
import com.talkable.presentation.feedback.today.model.TodayFeedback
import com.talkable.presentation.mypage.saved.Constants
import com.talkable.presentation.talk.feedback.model.toTodayFeedback


class TodayFeedbackFragment :
    BindingFragment<FragmentTodayFeedbackBinding>(R.layout.fragment_today_feedback) {

    private lateinit var todaySavedAdapter: TodayFeedbackAdapter
    private val viewModel: FeedbackViewModel by activityViewModels()

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
        val largestChipId = getLargestFeedbackChipId()
        binding.cgTodayFeedbackList.check(largestChipId)

        updateFeedbackData(largestChipId)

        binding.cgTodayFeedbackList.setOnCheckedStateChangeListener { chipGroup, _ ->
            updateFeedbackData(chipGroup.checkedChipId)
        }
    }

    private fun getLargestFeedbackChipId(): Int {
        val todayFeedback = viewModel.feedback.toTodayFeedback()
        val sizes = mapOf(
            R.id.chip_today_expression to todayFeedback.todayExpression.size,
            R.id.chip_today_grammar to todayFeedback.todayGrammar.size,
            R.id.chip_today_pronunciation to todayFeedback.todayPronunciation.size
        )
        return sizes.maxByOrNull { it.value }?.key ?: R.id.chip_today_expression
    }

    private fun updateFeedbackData(checkedChipId: Int) {
        val todayFeedback = viewModel.feedback.toTodayFeedback()
        val newData = when (checkedChipId) {
            R.id.chip_today_expression -> todayFeedback.todayExpression
            R.id.chip_today_grammar -> todayFeedback.todayGrammar
            R.id.chip_today_pronunciation -> todayFeedback.todayPronunciation
            else -> combineAllFeedbackData()
        }
        todaySavedAdapter.submitList(newData)
    }

    private fun combineAllFeedbackData(): List<TodayFeedback> {
        val todayFeedback = viewModel.feedback.toTodayFeedback()
        return todayFeedback.todayExpression +
                todayFeedback.todayGrammar +
                todayFeedback.todayPronunciation
    }

    companion object {
        fun newInstance(category: TodaySavedCategory): TodayFeedbackFragment {
            return TodayFeedbackFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Constants.KEY_CATEGORY, category)
                }
            }
        }
    }
}