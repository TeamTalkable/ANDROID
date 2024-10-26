package com.talkable.presentation.feedback

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentFeedbackPronunciationBinding
import kotlinx.coroutines.launch

class FeedbackPronunciationFragment :
    BindingFragment<FragmentFeedbackPronunciationBinding>(R.layout.fragment_feedback_pronunciation) {

    private var englishWord: String? = null
    private val viewModel: FeedbackViewModel by activityViewModels()

    override fun initView() {
        statusBarColorOf(R.color.white)
        collect()
        initRecordCancelClickListener()
        initRecordCheckClickListener()
        initNavigateToBackClickListener()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FeedbackUiState.PatchPronunciationFeedbacks -> {
                        binding.tvFeedbackPronunciationEnglish.text = uiState.answer
                        binding.tvFeedbackPronunciationAccuracy.text = "${uiState.score}%"
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun initNavigateToBackClickListener() {
        binding.tvFeedbackPronunciationBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecordCancelClickListener() {
        binding.ivFeedbackPronunciatoinTrash.setOnClickListener {

        }
    }

    private fun initRecordCheckClickListener() {
        binding.ivFeedbackPronunciationCheck.setOnClickListener {

            FeedbackPronunciationCompleteDialog().show(childFragmentManager, PRONUNCIATION_DIALOG)
        }
    }

    companion object {
        const val PRONUNCIATION_DIALOG = "pronunciationDialog"
        fun newInstance() = FeedbackPronunciationFragment()
    }
}