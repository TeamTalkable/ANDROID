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
    }

    companion object {
        val mockData = TalkFeedbackModel(
            talkFeedbackId = 1,
            talkTime = "10",
            flowerImage = "https://github.com/TeamTalkable/ANDROID/assets/98076050/812cf14d-daf6-46b6-a7ca-2e0eee900fd6",
            remainTime = "15",
            learnedExpression = listOf(
                Learned.Expression(
                    type = "표현", wordEnglish = "school", wordKorean = "n. 학교\n" + "v. 훈련시키다, 교육시키다"
                )
            ),
            learnedGrammar = listOf(
                Learned.Grammar(
                    type = "문법",
                    wrongGrammar = "take",
                    correctGrammar = "took",
                    reason = "과거의 사실을 얘기할 때는 동사의 과거형을 사용"
                )
            ),
            learnedPronunciation = listOf(
                Learned.Pronunciation(type = "발음", word = "science"),
                Learned.Pronunciation(type = "발음", word = "today")
            ),
            isGraphChanged = true
        )
    }
}