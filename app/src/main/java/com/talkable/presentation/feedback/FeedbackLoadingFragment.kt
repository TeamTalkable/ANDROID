package com.talkable.presentation.feedback

import android.os.Bundle
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
    private lateinit var nextQuestionEn: String
    private lateinit var nextQuestionKo: String
    private lateinit var beforeAnswer: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nextQuestionEn = arguments?.getString(FEEDBACK_QUESTION_EN).orEmpty()
        nextQuestionKo = arguments?.getString(FEEDBACK_QUESTION_KO).orEmpty()
        beforeAnswer = arguments?.getString(FEEDBACK_BEFORE).orEmpty()
    }

    override fun initView() {
        statusBarColorOf(R.color.white)
        viewModel.patchGptFeedbacks(beforeAnswer)
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
                FEEDBACK_QUESTION_EN to nextQuestionEn,
                FEEDBACK_QUESTION_KO to nextQuestionKo,
                FEEDBACK_BEFORE to beforeAnswer,
            )
        )
    }
}