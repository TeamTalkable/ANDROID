package com.talkable.presentation.feedback.today

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentTodayFeedbackBinding
import com.talkable.presentation.feedback.FeedbackViewModel
import com.talkable.presentation.feedback.today.model.TodayFeedback
import com.talkable.presentation.mypage.saved.Constants
import com.talkable.presentation.talk.feedback.FinalFeedbackUiState
import com.talkable.presentation.talk.feedback.FinalTalkFeedbackViewModel
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import com.talkable.presentation.talk.feedback.model.toTodayFeedback
import kotlinx.coroutines.launch

class TodayFeedbackFragment :
    BindingFragment<FragmentTodayFeedbackBinding>(R.layout.fragment_today_feedback) {

    private lateinit var todaySavedAdapter: TodayFeedbackAdapter
    private val feedbackViewModel: FeedbackViewModel by activityViewModels()
    private val viewModel: FinalTalkFeedbackViewModel by activityViewModels()

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initTodayFeedbackAdapter()
        initFeedbackChipClickListener2()

        if (todaySavedAdapter.itemCount == 0) {
            setupRecyclerView()
            collectFeedbackData()
        }
    }

    private fun setupRecyclerView() {
        todaySavedAdapter = TodayFeedbackAdapter(requireContext())
        with(binding.rvSavedWord) {
            adapter = todaySavedAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun collectFeedbackData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is FinalFeedbackUiState.Success -> {
                            val feedbackList = combineAllFeedbackData(uiState.data).toList()
                            todaySavedAdapter.submitList(feedbackList)
                            initFeedbackChipClickListener(uiState.data)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun initFeedbackChipClickListener(data: TalkFeedbackModel) {
        val largestChipId = getLargestFeedbackChipId(data)
        binding.cgTodayFeedbackList.check(largestChipId)
        updateFeedbackData(largestChipId, data)
        binding.cgTodayFeedbackList.setOnCheckedStateChangeListener { chipGroup, _ ->
            updateFeedbackData(chipGroup.checkedChipId, data)
        }
    }

    private fun getLargestFeedbackChipId(data: TalkFeedbackModel): Int {
        val sizes = mapOf(
            R.id.chip_today_expression to data.learnedExpression.size,
            R.id.chip_today_grammar to data.learnedGrammar.size,
            R.id.chip_today_pronunciation to data.learnedPronunciation.size
        )
        return sizes.maxByOrNull { it.value }?.key ?: R.id.chip_today_expression
    }

    private fun updateFeedbackData(checkedChipId: Int, data: TalkFeedbackModel) {
        val newData: List<TodayFeedback> = when (checkedChipId) {
            R.id.chip_today_expression -> data.learnedExpression.map {
                TodayFeedback.Expression(
                    type = it.type,
                    english = it.wordEnglish,
                    translation = it.wordKorean,
                    feedbackBefore = it.wordEnglish,
                    feedbackAfter = it.expressionAfterAnswer.afterFullAnswer
                )
            }

            R.id.chip_today_grammar -> data.learnedGrammar.map {
                TodayFeedback.Grammar(
                    type = it.type,
                    wrong = it.wrongGrammar,
                    correct = it.correctGrammar,
                    reason = it.reason,
                    feedbackBefore = data.feedbackBefore,
                    feedbackAfter = it.grammarAfterAnswer.afterFullAnswer
                )
            }

            R.id.chip_today_pronunciation -> data.learnedPronunciation.map {
                TodayFeedback.Pronunciation(
                    type = it.type,
                    word = it.englishWord,
                    pronunciation = it.pronunciationEnglish,
                    translation = it.koreanWord,
                    sentence = data.feedbackBefore,
                    accuracy = it.wordAccuracy?.replace("%", "")?.toIntOrNull() ?: 0
                )
            }

            else -> combineAllFeedbackData(data)
        }
        todaySavedAdapter.submitList(newData.toList())
    }

    private fun combineAllFeedbackData(data: TalkFeedbackModel): List<TodayFeedback> {
        val expressions = data.learnedExpression.map {
            TodayFeedback.Expression(
                type = it.type,
                english = it.wordEnglish,
                translation = it.wordKorean,
                feedbackBefore = it.wordEnglish,
                feedbackAfter = it.expressionAfterAnswer.afterFullAnswer
            )
        }

        val grammars = data.learnedGrammar.map {
            TodayFeedback.Grammar(
                type = it.type,
                wrong = it.wrongGrammar,
                correct = it.correctGrammar,
                reason = it.reason,
                feedbackBefore = data.feedbackBefore,
                feedbackAfter = it.grammarAfterAnswer.afterFullAnswer
            )
        }

        val pronunciations = data.learnedPronunciation.map {
            TodayFeedback.Pronunciation(
                type = it.type,
                word = it.englishWord,
                pronunciation = it.pronunciationEnglish,
                translation = it.koreanWord,
                sentence = data.feedbackBefore,
                accuracy = it.wordAccuracy?.replace("%", "")?.toIntOrNull() ?: 0
            )
        }

        return expressions + grammars + pronunciations
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

    private fun initTodayFeedbackAdapter() {
        todaySavedAdapter = TodayFeedbackAdapter(requireContext())
        with(binding.rvSavedWord) {
            adapter = todaySavedAdapter
            layoutManager = LinearLayoutManager(context)
        }
        val feedbackData = combineAllFeedbackData()

        if (feedbackData.isNotEmpty()) {
            todaySavedAdapter.submitList(feedbackData)
        }
    }

    private fun initFeedbackChipClickListener2() {
        val largestChipId = getLargestFeedbackChipId()
        binding.cgTodayFeedbackList.check(largestChipId)

        val todayFeedback = feedbackViewModel.feedback.toTodayFeedback()

        if (todayFeedback.todayExpression.isNotEmpty() ||
            todayFeedback.todayGrammar.isNotEmpty() ||
            todayFeedback.todayPronunciation.isNotEmpty()
        ) {
            updateFeedbackData(largestChipId)
        }

        binding.cgTodayFeedbackList.setOnCheckedStateChangeListener { chipGroup, _ ->
            updateFeedbackData(chipGroup.checkedChipId)
        }
    }

    private fun getLargestFeedbackChipId(): Int {
        val todayFeedback = feedbackViewModel.feedback.toTodayFeedback()
        val sizes = mapOf(
            R.id.chip_today_expression to todayFeedback.todayExpression.size,
            R.id.chip_today_grammar to todayFeedback.todayGrammar.size,
            R.id.chip_today_pronunciation to todayFeedback.todayPronunciation.size
        )
        return sizes.maxByOrNull { it.value }?.key ?: R.id.chip_today_expression
    }

    private fun updateFeedbackData(checkedChipId: Int) {
        val todayFeedback = feedbackViewModel.feedback.toTodayFeedback()
        val newData = when (checkedChipId) {
            R.id.chip_today_expression -> todayFeedback.todayExpression
            R.id.chip_today_grammar -> todayFeedback.todayGrammar
            R.id.chip_today_pronunciation -> todayFeedback.todayPronunciation
            else -> combineAllFeedbackData()
        }
        todaySavedAdapter.submitList(newData)
    }

    private fun combineAllFeedbackData(): List<TodayFeedback> {
        val todayFeedback = feedbackViewModel.feedback.toTodayFeedback()
        return todayFeedback.todayExpression +
                todayFeedback.todayGrammar +
                todayFeedback.todayPronunciation
    }
}