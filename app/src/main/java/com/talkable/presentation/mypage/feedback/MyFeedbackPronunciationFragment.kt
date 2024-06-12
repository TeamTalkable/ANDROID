package com.talkable.presentation.mypage.feedback

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentMyFeedbackPronunciationBinding

class MyFeedbackPronunciationFragment : BindingFragment<FragmentMyFeedbackPronunciationBinding>(R.layout.fragment_my_feedback_pronunciation) {
    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initCategoryTextViewClickListener()
        initMyFeedbackAdapter()
    }

    private fun initCategoryTextViewClickListener() {
        binding.tvMyFeedbackPronunciationCategory.setOnClickListener {
            navigateToFeedbackDialog()
        }
    }

    private fun initMyFeedbackAdapter() {
        with(binding.rvMyFeedbackPronunciation) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPronunciationAdapter(mockDataList)
        }
    }
    private fun navigateToFeedbackDialog() {
        findNavController().navigate(R.id.action_my_page_feedback_to_my_page_feedback_dialog)
    }

    companion object {
        private val mockDataList = listOf(
            "today",
            "I took a science class today.",
            "today",
            "I took a science class today.",
            "today",
            "I took a science class today.",
            "today",
            "I took a science class today.",
            "today",
            "I took a science class today.",
            "today",
            "I took a science class today."
        )
    }
}