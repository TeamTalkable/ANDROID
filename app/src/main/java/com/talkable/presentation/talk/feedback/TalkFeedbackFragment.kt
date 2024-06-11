package com.talkable.presentation.talk.feedback

import androidx.recyclerview.widget.ConcatAdapter
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentTalkFeedbackBinding
import com.talkable.presentation.talk.feedback.mapper.toLearnedList
import com.talkable.presentation.talk.feedback.model.Learned
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

class TalkFeedbackFragment :
    BindingFragment<FragmentTalkFeedbackBinding>(R.layout.fragment_talk_feedback) {
    private lateinit var feedbackTopAdapter: TalkFeedbackTopAdapter
    private lateinit var feedbackLearnedAdapter: TalkFeedbackLearnedAdapter
    private lateinit var feedbackBottomAdapter: TalkFeedbackBottomAdapter

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initFeedbackTopAdapter()
        initFeedbackLearnedAdapter()
        initFeedbackBottomAdapter()
        concatFeedbackAdapters()
    }

    private fun initFeedbackTopAdapter() {
        feedbackTopAdapter = TalkFeedbackTopAdapter(
            context = requireContext()
        ).apply { submitList(listOf(mockData)) }
    }

    private fun initFeedbackLearnedAdapter() {
        feedbackLearnedAdapter = TalkFeedbackLearnedAdapter(
            context = requireContext()
        ).apply { submitList(mockData.toLearnedList()) }
    }

    private fun initFeedbackBottomAdapter() {
        feedbackBottomAdapter = TalkFeedbackBottomAdapter(
            context = requireContext()
        ).apply { submitList(listOf(mockData)) }
    }

    private fun concatFeedbackAdapters() {
        if (::feedbackTopAdapter.isInitialized && ::feedbackLearnedAdapter.isInitialized && ::feedbackBottomAdapter.isInitialized) {
            binding.rvTalkFeedback.adapter =
                ConcatAdapter(feedbackTopAdapter, feedbackLearnedAdapter, feedbackBottomAdapter)
        }
        setRecyclerviewItemDecoration()
    }

    private fun setRecyclerviewItemDecoration() {
        if (binding.rvTalkFeedback.itemDecorationCount == 0) {
            binding.rvTalkFeedback.addItemDecoration(
                TalkFeedbackDecorator(requireContext(), mockData.learnedExpression.size + 4),
            )
        }
    }

    companion object {
        val mockData = TalkFeedbackModel(
            talkFeedbackId = 1,
            talkTime = "10",
            flowerImage = "https://github.com/TeamTalkable/ANDROID/assets/98076050/7bb68364-796d-4aec-9ffb-72c4e8625b3e",
            remainTime = "15",
            learnedAfterAnswer = listOf(
                Learned.AfterAnswer(
                    type = "0",
                    afterFullAnswer = "What I like most about living here is the proximity to park, which allows me to enjoy nature.",
                    afterAnswerParts = listOf(
                        "What I like most about living here is the proximity to",
                        "which allows me to enjoy"
                    )
                ),
                Learned.AfterAnswer(
                    type = "0",
                    afterFullAnswer = "I took a science class today",
                    afterAnswerParts = listOf(
                        "took"
                    )
                ),
            ),
            learnedExpression = listOf(
                Learned.Expression(
                    type = "학습한 표현",
                    wordEnglish = "the proximity to Central Park",
                    wordKorean = "중앙공원과의 가까운 거리’ 를 의미하는 표현"
                ),
                Learned.Expression(
                    type = "학습한 표현",
                    wordEnglish = "which allows me to enjoy nature",
                    wordKorean = "이로 인해 자연을 즐길 수 있다는 점을 강조하는 표현"
                ),
                Learned.Expression(
                    type = "학습한 표현",
                    wordEnglish = "enjoy nature",
                    wordKorean = "자연을 즐기다'라는 자연스러운 표현"
                )
            ),
            learnedGrammar = listOf(
                Learned.Grammar(
                    type = "학습한 문법",
                    wrongGrammar = "take",
                    correctGrammar = "took",
                    reason = "과거의 사실을 얘기할 때는 동사의 과거형을 사용"
                )
            ),
            learnedPronunciation = listOf(
                Learned.Pronunciation(
                    type = "학습한 발음",
                    englishWord = "science",
                    pronunciationEnglish = "[ ˈsaɪəns ]",
                    koreanWord = "과학"
                ),
                Learned.Pronunciation(
                    type = "학습한 발음",
                    englishWord = "today",
                    pronunciationEnglish = "[ ˈtodai ]",
                    koreanWord = "오늘"
                )
            ),
            isGraphChanged = true
        )
    }
}