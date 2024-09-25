package com.talkable.presentation.feedback

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key.FEEDBACK_BEFORE
import com.talkable.core.util.Key.FEEDBACK_QUESTION_EN
import com.talkable.core.util.Key.FEEDBACK_QUESTION_KO
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentFeedbackLoadingBinding
import kotlinx.coroutines.launch

class FeedbackLoadingFragment :
    BindingFragment<FragmentFeedbackLoadingBinding>(R.layout.fragment_feedback_loading) {

    private val viewModel: FeedbackViewModel by activityViewModels()

    override fun initView() {
        statusBarColorOf(R.color.white)
        viewModel.patchGptFeedbacks( "I went class in the morning and study in the library.")
        collect()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FeedbackUiState.PatchGptFeedbacks -> {
                        navigateToExpressionFeedbackFragment()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun navigateToExpressionFeedbackFragment() {
        findNavController().navigate(
            R.id.action_feedback_loading_to_feedback_expression, bundleOf(
                FEEDBACK_QUESTION_EN to "What did you do at school today?",
                FEEDBACK_QUESTION_KO to "학교에서 오늘 뭐했어?",
                FEEDBACK_BEFORE to "I went class in the morning and study in the library."
            )
        )
    }
}