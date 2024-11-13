package com.talkable.presentation.home

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentTalkReviewBinding
import com.talkable.presentation.feedback.today.TodaySavedFragment.Companion.FEEDBACK_TAB_FEEDBACK
import com.talkable.presentation.feedback.today.TodaySavedFragment.Companion.FEEDBACK_TAB_SAVED

class TalkReviewFragment :
    BindingFragment<FragmentTalkReviewBinding>(R.layout.fragment_talk_review) {

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initTalkSavedBtnClickListener()
        initTalkFeedbackBtnClickListener()
        initBackBtnClickListener()
    }

    private fun initTalkSavedBtnClickListener() {
        binding.btnTalkSavedCount.setOnClickListener {
            navigateToSavedFragment()
        }
    }

    private fun initTalkFeedbackBtnClickListener() {
        binding.btnTalkFeedbackCount.setOnClickListener {
            navigateToFeedbackFragment()
        }
    }

    private fun initBackBtnClickListener() {
        binding.appbarTalkReview.ivReviewBack.setOnClickListener {
            navigateToBack()
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    private fun navigateToSavedFragment() {
        findNavController().navigate(
            R.id.action_fragment_talk_review_to_today_saved,
            bundleOf(Key.SAVED_TAB_INDEX to FEEDBACK_TAB_SAVED)
        )
    }

    private fun navigateToFeedbackFragment() {
        findNavController().navigate(
            R.id.action_fragment_talk_review_to_today_saved,
            bundleOf(Key.SAVED_TAB_INDEX to FEEDBACK_TAB_FEEDBACK)
        )
    }
}