package com.talkable.presentation.feedback

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.context.pxToDp
import com.talkable.core.util.fragment.colorOf
import com.talkable.databinding.FragmentTalkFeedbackExpressionBinding
import com.talkable.presentation.feedback.model.FeedbackExpressionModel
import com.talkable.presentation.feedback.model.FeedbackGrammarModel
import com.talkable.presentation.talk.feedback.TalkFeedbackLearnedAdapter
import com.talkable.presentation.talk.feedback.model.Learned

class FeedbackExpressionFragment :
    BindingFragment<FragmentTalkFeedbackExpressionBinding>(R.layout.fragment_talk_feedback_expression) {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    override fun initView() {
        initCommentBottomSheet()
        initFeedbackAdapter()
        setAfterAnswerTextColor(
            mockExpressionData.afterFullAnswer,
            mockExpressionData.afterAnswerParts
        )
        binding.model = mockExpressionData
    }

    private fun initCommentBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layoutBottomSheet.root)
        bottomSheetBehavior.expandedOffset = requireContext().pxToDp(20)
        bottomSheetBehavior.halfExpandedRatio = 0.2F
    }

    private fun initFeedbackAdapter() {
        binding.layoutBottomSheet.rvBottomSheetTalkFeedback.adapter = TalkFeedbackLearnedAdapter(
            context = requireContext()
        ).apply { submitList(mockExpressionData.learnedExpression) }
    }

    private fun setAfterAnswerTextColor(fullText: String, partsText: List<String>) {
        val spannableString = SpannableString(fullText)

        partsText.forEach { part ->
            val startIndex = fullText.indexOf(part)
            if (startIndex != -1) {
                val endIndex = startIndex + part.length
                val span = ForegroundColorSpan(colorOf(R.color.main_4))
                spannableString.setSpan(
                    span,
                    startIndex,
                    endIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        binding.tvTalkFeedbackAfter.text = spannableString
    }

    companion object {
        val mockExpressionData = FeedbackExpressionModel(
            feedbackId = 1,
            question = "What did you do today in school?",
            beforeAnswer = "Near my house, I have park so I can join the nature.",
            afterFullAnswer = "What I like most about living here is the proximity to park, which allows me to enjoy nature.",
            afterAnswerParts = listOf(
                "What I like most about living here is the proximity to",
                "which allows me to enjoy"
            ),
            learnedExpression = listOf(
                Learned.Expression(
                    type = "변경된 표현",
                    wordEnglish = "the proximity to Central Park",
                    wordKorean = "중앙공원과의 가까운 거리’ 를 의미하는 표현"
                ),
                Learned.Expression(
                    type = "변경된 표현",
                    wordEnglish = "which allows me to enjoy nature",
                    wordKorean = "이로 인해 자연을 즐길 수 있다는 점을 강조하는 표현"
                ),
                Learned.Expression(
                    type = "변경된 표현",
                    wordEnglish = "enjoy nature",
                    wordKorean = "자연을 즐기다'라는 자연스러운 표현"
                )
            ),
        )

        val mockGrammarData = FeedbackGrammarModel(
            feedbackId = 2,
            question = "What did you do today in school?",
            beforeAnswer = "I take a science class today.",
            afterFullAnswer = "I took a science class today",
            afterAnswerParts = listOf(
                "took"
            ),
            learnedGrammar = listOf(
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