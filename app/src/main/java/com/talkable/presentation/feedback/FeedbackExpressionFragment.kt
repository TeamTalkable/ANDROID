package com.talkable.presentation.feedback

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentTalkFeedbackExpressionBinding
import com.talkable.presentation.FeedbackTextColor
import com.talkable.presentation.talk.feedback.TalkFeedbackLearnedAdapter
import com.talkable.presentation.talk.feedback.model.Learned
import kotlinx.coroutines.launch

class FeedbackExpressionFragment :
    BindingFragment<FragmentTalkFeedbackExpressionBinding>(R.layout.fragment_talk_feedback_expression) {

    private val viewModel: FeedbackViewModel by activityViewModels()
    private lateinit var questionEn: String
    private lateinit var questionKo: String
    private lateinit var beforeAnswer: String
    private lateinit var nextQuestionEn: String
    private lateinit var nextQuestionKo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionEn = arguments?.getString(Key.FEEDBACK_QUESTION_EN).orEmpty()
        questionKo = arguments?.getString(Key.FEEDBACK_QUESTION_KO).orEmpty()
        beforeAnswer = arguments?.getString(Key.FEEDBACK_BEFORE).orEmpty()
    }

    override fun initView() {
        collect()
        statusBarColorOf(R.color.white)

        initBackBtnClickListener()
        initAppbarNextClickListener()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FeedbackUiState.PatchGptFeedbacks -> {
                        setLayout(uiState.data.afterFullAnswer, uiState.data.afterAnswerParts)
                        initFeedbackAdapter(createLearnedListWithLabels(uiState.data.feedback))
                        nextQuestionEn = uiState.data.nextQuestionEn
                        nextQuestionKo = uiState.data.nextQuestionKo
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun setLayout(afterFull: String, afterParts: List<String>) = with(binding) {
        tvTalkFeedbackExpressionQuestion.text = questionEn
        tvTalkFeedbackExpressionKorean.text = questionKo
        tvTalkFeedbackExpressionBefore.text = beforeAnswer
        tvTalkFeedbackAfter.text = afterFull
        setAfterAnswerTextColor(
            afterFull,
            afterParts
        )
    }

    private fun createLearnedListWithLabels(feedbackMap: Map<String, List<Learned>>): List<Learned> {
        val resultList = mutableListOf<Learned>()

        if (feedbackMap.containsKey("Expression")) {
            resultList.add(Learned.Label(type = "표현 피드백"))
            resultList.addAll(feedbackMap["Expression"] ?: emptyList())
        }

        if (feedbackMap.containsKey("Grammar")) {
            resultList.add(Learned.Label(type = "문법 피드백"))
            resultList.addAll(feedbackMap["Grammar"] ?: emptyList())
        }

        return resultList
    }

    private fun initFeedbackAdapter(data: List<Learned>) {
        binding.layoutBottomSheet.rvBottomSheetTalkFeedback.adapter = TalkFeedbackLearnedAdapter(
            context = requireContext()
        ).apply { submitList(data) }
        setRecyclerviewItemDecoration()
    }

    private fun setRecyclerviewItemDecoration() {
        if (binding.layoutBottomSheet.rvBottomSheetTalkFeedback.itemDecorationCount == 0) {
            binding.layoutBottomSheet.rvBottomSheetTalkFeedback.addItemDecoration(
                FeedbackDecorator(requireContext()),
            )
        }
    }

    private fun setAfterAnswerTextColor(fullText: String, partsText: List<String>) {
        val spannableString =
            FeedbackTextColor(requireContext()).setAfterAnswerTextColor(fullText, partsText)

        binding.tvTalkFeedbackAfter.text = spannableString
    }

    private fun initBackBtnClickListener() {
        binding.btnFeedbackExpressionBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAppbarNextClickListener() {
        binding.btnFeedbackExpressionNext.setOnClickListener {
            navigateToPronunciationFeedbackFragment()
        }
    }


    private fun navigateToPronunciationFeedbackFragment() =
        findNavController().navigate(
            R.id.action_feedback_expression_to_feedback_pronunciation, bundleOf(
                Key.FEEDBACK_QUESTION_EN to nextQuestionEn,
                Key.FEEDBACK_QUESTION_KO to nextQuestionKo,
                Key.FEEDBACK_AFTER to binding.tvTalkFeedbackAfter.text.toString()
            )
        )
}