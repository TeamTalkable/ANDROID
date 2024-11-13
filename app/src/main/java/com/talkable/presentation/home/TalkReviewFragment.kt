package com.talkable.presentation.home

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
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
import com.talkable.presentation.talk.feedback.FinalFeedbackUiState
import com.talkable.presentation.talk.feedback.FinalTalkFeedbackViewModel
import com.talkable.presentation.talk.feedback.TalkFeedbackLearnedAdapter
import com.talkable.presentation.talk.feedback.mapper.toLearnedList
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import kotlinx.coroutines.launch

class TalkReviewFragment :
    BindingFragment<FragmentTalkReviewBinding>(R.layout.fragment_talk_review) {

    private lateinit var feedbackLearnedAdapter: TalkFeedbackLearnedAdapter

    private val viewModel: FinalTalkFeedbackViewModel by viewModels({ activity as AppCompatActivity })

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        collect()
        initTalkSavedBtnClickListener()
        initTalkFeedbackBtnClickListener()
        initBackBtnClickListener()
        setSavedCount()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FinalFeedbackUiState.Success -> {
                        initFeedbackLearnedAdapter(uiState.data)
                        setFeedbackAdapter(uiState.data)
                        setFeedbackCount(calculateFeedbackCount(uiState.data))
                    }

                    else -> Unit
                }
            }
        }
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

    private fun setFeedbackAdapter(data: TalkFeedbackModel) {
        binding.rvTalkFeedback.adapter = ConcatAdapter(feedbackLearnedAdapter)
        setRecyclerviewItemDecoration(data)
    }

    //TODO : Firebase 연결
    private fun setSavedCount() {
        binding.btnTalkSavedCount.text = getString(R.string.tv_home_learning_storage, 3)
    }

    private fun setFeedbackCount(totalFeedbackCount: Int) {
        binding.btnTalkFeedbackCount.text =
            getString(R.string.tv_home_learning_feedback, totalFeedbackCount)
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