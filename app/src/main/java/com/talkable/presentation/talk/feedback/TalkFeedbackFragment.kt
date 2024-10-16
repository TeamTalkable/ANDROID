package com.talkable.presentation.talk.feedback

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
import com.talkable.databinding.FragmentTalkFeedbackBinding
import com.talkable.presentation.mypage.MyPageFragment
import com.talkable.presentation.talk.feedback.mapper.toLearnedList
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import kotlinx.coroutines.launch

class TalkFeedbackFragment :
    BindingFragment<FragmentTalkFeedbackBinding>(R.layout.fragment_talk_feedback) {
    private lateinit var feedbackTopAdapter: TalkFeedbackTopAdapter
    private lateinit var feedbackLearnedAdapter: TalkFeedbackLearnedAdapter
    private lateinit var feedbackBottomAdapter: TalkFeedbackBottomAdapter

    private val viewModel: FinalTalkFeedbackViewModel by viewModels()

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        collect()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FinalFeedbackUiState.Success -> {
                        initFeedbackTopAdapter(uiState.data)
                        initFeedbackLearnedAdapter(uiState.data)
                        initFeedbackBottomAdapter(uiState.data)
                        concatFeedbackAdapters(uiState.data)
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun initFeedbackTopAdapter(data: TalkFeedbackModel) {
        feedbackTopAdapter = TalkFeedbackTopAdapter(
            context = requireContext()
        ).apply { submitList(listOf(data)) }
    }

    private fun initFeedbackLearnedAdapter(data: TalkFeedbackModel) {
        feedbackLearnedAdapter = TalkFeedbackLearnedAdapter(
            context = requireContext()
        ).apply { submitList(data.toLearnedList()) }
    }

    private fun initFeedbackBottomAdapter(data: TalkFeedbackModel) {
        feedbackBottomAdapter = TalkFeedbackBottomAdapter(
            context = requireContext(), ::navigateToMyPageFragment, ::navigateToHomeFragment
        ).apply { submitList(listOf(data)) }
    }

    private fun navigateToMyPageFragment() {
        findNavController().navigate(
            R.id.action_fragment_talk_feedback_to_fragment_my_page_chart_detail, bundleOf(
                Key.CHART_KEY to MyPageFragment.mockData.chartData
            )
        )
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_talk_feedback_to_home)
    }

    private fun concatFeedbackAdapters(data: TalkFeedbackModel) {
        if (::feedbackTopAdapter.isInitialized && ::feedbackLearnedAdapter.isInitialized && ::feedbackBottomAdapter.isInitialized) {
            binding.rvTalkFeedback.adapter =
                ConcatAdapter(feedbackTopAdapter, feedbackLearnedAdapter, feedbackBottomAdapter)
        }
        setRecyclerviewItemDecoration(data)
    }

    private fun setRecyclerviewItemDecoration(data: TalkFeedbackModel) {
        if (binding.rvTalkFeedback.itemDecorationCount == 0) {
            binding.rvTalkFeedback.addItemDecoration(
                TalkFeedbackDecorator(requireContext(), data.learnedExpression.size + 4),
            )
        }
    }
}