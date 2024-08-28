package com.talkable.presentation.feedback

import android.content.res.Resources
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.context.pxToDp
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentTalkFeedbackExpressionBinding
import com.talkable.presentation.FeedbackTextColor
import com.talkable.presentation.feedback.model.FeedbackExpressionModel
import com.talkable.presentation.talk.feedback.TalkFeedbackLearnedAdapter
import com.talkable.presentation.talk.feedback.model.Learned

class FeedbackExpressionFragment :
    BindingFragment<FragmentTalkFeedbackExpressionBinding>(R.layout.fragment_talk_feedback_expression) {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    override fun initView() {
        statusBarColorOf(R.color.white)
        initCommentBottomSheet()
        initFeedbackAdapter()
        setAfterAnswerTextColor(
            mockExpressionData.afterFullAnswer,
            mockExpressionData.afterAnswerParts
        )
        binding.model = mockExpressionData
        setAppBar()
        initAppbarNextClickListener()
    }

    private fun initCommentBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layoutBottomSheet.root)
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.expandedOffset = requireContext().pxToDp(80)
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val halfScreenHeight = screenHeight / 3

        bottomSheetBehavior.peekHeight = halfScreenHeight
    }

    private fun initFeedbackAdapter() {
        binding.layoutBottomSheet.rvBottomSheetTalkFeedback.adapter = TalkFeedbackLearnedAdapter(
            context = requireContext()
        ).apply { submitList(mockExpressionData.learnedExpression) }
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

    private fun setAppBar() {
        binding.appBarTalkFeedbackExpression.leftBackIsVisible = false
        binding.appBarTalkFeedbackExpression.rightBackIsVisible = true
    }

    private fun initAppbarNextClickListener() {
        binding.appBarTalkFeedbackExpression.ivAppBarNext.setOnClickListener {
            navigateToPronunciationFeedbackFragment()
        }
    }

    private fun navigateToPronunciationFeedbackFragment() =
        findNavController().navigate(R.id.action_feedback_expression_to_feedback_pronunciation)

    companion object {
        val mockExpressionData = FeedbackExpressionModel(
            feedbackId = 1,
            question = "What did you do today in school?",
            korean = "너는 오늘 학교에서 무엇을 했어?",
            beforeAnswer = "Near my house, I have park so I can join the nature.",
            afterFullAnswer = "What I like most about living here is the proximity to park, which allows me to enjoy nature.",
            afterAnswerParts = listOf(
                "What I like most about living here is the proximity to",
                "which allows me to enjoy"
            ),
            learnedExpression = listOf(
                Learned.Label(
                    type = "표현 피드백"
                ),
                Learned.Expression(
                    type = "표현 피드백",
                    wordEnglish = "the proximity to Central Park",
                    wordKorean = "중앙공원과의 가까운 거리’ 를 의미하는 표현"
                ),
                Learned.Expression(
                    type = "표현 피드백",
                    wordEnglish = "which allows me to enjoy nature",
                    wordKorean = "이로 인해 자연을 즐길 수 있다는 점을 강조하는 표현"
                ),
                Learned.Expression(
                    type = "표현 피드백",
                    wordEnglish = "enjoy nature",
                    wordKorean = "자연을 즐기다'라는 자연스러운 표현"
                ),
                Learned.Label(
                    type = "문법 피드백"
                ),
                Learned.Grammar(
                    type = "동사의 시제",
                    wrongGrammar = "take",
                    correctGrammar = "took",
                    reason = "과거의 사실을 얘기할 때는 동사의 과거형을 사용"
                ),
                Learned.Grammar(
                    type = "동사의 수일치",
                    wrongGrammar = "take",
                    correctGrammar = "takes",
                    reason = "과거의 사실을 얘기할 때는 동사의 과거형을 사용"
                ),
            )
        )
    }
}