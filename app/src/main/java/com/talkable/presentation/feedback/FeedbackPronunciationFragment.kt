package com.talkable.presentation.feedback

import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentFeedbackPronunciationBinding
import com.talkable.databinding.FragmentTalkFeedbackExpressionBinding

class FeedbackPronunciationFragment:
    BindingFragment<FragmentFeedbackPronunciationBinding>(R.layout.fragment_feedback_pronunciation)  {
    override fun initView() {

    }

    private fun initPronunciationWordAdapter(){
        binding.rvFeedbackPronunciation.adapter =
    }
}