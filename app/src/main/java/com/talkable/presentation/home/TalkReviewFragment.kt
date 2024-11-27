package com.talkable.presentation.home

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentTalkReviewBinding
import com.talkable.presentation.feedback.today.TodaySavedFragment.Companion.FEEDBACK_TAB_FEEDBACK
import com.talkable.presentation.feedback.today.TodaySavedFragment.Companion.FEEDBACK_TAB_SAVED
import com.talkable.presentation.home.model.TalkSavedModel
import com.talkable.presentation.talk.feedback.FinalFeedbackUiState
import com.talkable.presentation.talk.feedback.FinalTalkFeedbackViewModel
import com.talkable.presentation.talk.feedback.TalkFeedbackLearnedAdapter
import com.talkable.presentation.talk.feedback.mapper.toLearnedList
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import kotlinx.coroutines.launch

class TalkReviewFragment :
    BindingFragment<FragmentTalkReviewBinding>(R.layout.fragment_talk_review) {
    private lateinit var reviewTopAdapter: TalkReviewTopAdapter
    private lateinit var feedbackLearnedAdapter: TalkFeedbackLearnedAdapter

    private val viewModel: FinalTalkFeedbackViewModel by activityViewModels()
    private val savedViewModel: TodaySavedViewModel by activityViewModels()

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        collectFeedback()
        collectSaved()
        initTalkSavedBtnClickListener()
        initTalkFeedbackBtnClickListener()
        initBackBtnClickListener()
    }

    private fun collectFeedback() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FinalFeedbackUiState.Success -> {
                        initReviewTopAdapter(uiState.data)
                        initFeedbackLearnedAdapter(uiState.data)
                        setFeedbackAdapters(uiState.data)
                        setFeedbackCount(calculateFeedbackCount(uiState.data))
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun collectSaved() {
        viewLifeCycleScope.launch {
            savedViewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is TodaySavedUiState.Success -> {
                        setSavedCount(calculateSavedCount(uiState.data))
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun initReviewTopAdapter(data: TalkFeedbackModel) {
        reviewTopAdapter = TalkReviewTopAdapter(
            context = requireContext()
        ).apply { submitList(listOf(data)) }
    }

    private fun initFeedbackLearnedAdapter(data: TalkFeedbackModel) {
        feedbackLearnedAdapter =
            TalkFeedbackLearnedAdapter(context = requireContext()).apply { submitList(data.toLearnedList()) }
    }

    private fun initTalkSavedBtnClickListener() {
        binding.btnTalkSavedCount.setOnClickListener {
            navigateToSavedFragment()
        }
    }

    private fun initTalkFeedbackBtnClickListener() {
        binding.btnTalkFeedbackCount.setOnClickListener {
            navigateToFeedbackFragment()
        }
    }

    private fun initBackBtnClickListener() {
        binding.appbarTalkReview.ivReviewBack.setOnClickListener {
            navigateToBack()
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    private fun navigateToSavedFragment() {
        findNavController().navigate(
            R.id.action_fragment_talk_review_to_today_saved,
            bundleOf(Key.SAVED_TAB_INDEX to FEEDBACK_TAB_SAVED)
        )
    }

    private fun navigateToFeedbackFragment() {
        findNavController().navigate(
            R.id.action_fragment_talk_review_to_today_saved,
            bundleOf(Key.SAVED_TAB_INDEX to FEEDBACK_TAB_FEEDBACK)
        )
    }

    private fun setFeedbackAdapters(data: TalkFeedbackModel) {
        if (::reviewTopAdapter.isInitialized && ::feedbackLearnedAdapter.isInitialized) {
            binding.rvTalkFeedback.adapter =
                ConcatAdapter(reviewTopAdapter, feedbackLearnedAdapter)
        }
        setRecyclerviewItemDecoration(data)
    }

    private fun setSavedCount(totalSavedCount: Int) {
        binding.btnTalkSavedCount.text =
            getString(R.string.tv_home_learning_storage, totalSavedCount)
    }

    private fun setFeedbackCount(totalFeedbackCount: Int) {
        binding.btnTalkFeedbackCount.text =
            getString(R.string.tv_home_learning_feedback, totalFeedbackCount)
    }

    private fun calculateSavedCount(data: TalkSavedModel): Int {
        return data.savedWordList.size +
                data.savedSentenceList.size
    }

    private fun calculateFeedbackCount(data: TalkFeedbackModel): Int {
        return data.learnedExpression.size +
                data.learnedGrammar.size +
                data.learnedPronunciation.size
    }

    private fun setRecyclerviewItemDecoration(data: TalkFeedbackModel) {
        if (binding.rvTalkFeedback.itemDecorationCount == 0) {
            binding.rvTalkFeedback.addItemDecoration(
                TalkReviewFeedbackDecorator(requireContext(), data.toLearnedList())
            )
        }
    }
}