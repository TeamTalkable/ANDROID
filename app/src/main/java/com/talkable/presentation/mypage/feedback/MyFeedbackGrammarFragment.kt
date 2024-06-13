package com.talkable.presentation.mypage.feedback

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentMyFeedbackGrammarBinding
import com.talkable.presentation.mypage.feedback.data.Grammar

class MyFeedbackGrammarFragment :
    BindingFragment<FragmentMyFeedbackGrammarBinding>(R.layout.fragment_my_feedback_grammar) {
    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initLatestTextViewClickListener()
        initCategoryTextViewClickListener()
        initMyFeedbackAdapter()
    }

    private fun initLatestTextViewClickListener() {
        with(binding) {
            tvMyFeedbackGrammarLatest.setOnClickListener {
                tvMyFeedbackGrammarLatest.isSelected = !tvMyFeedbackGrammarLatest.isSelected
                navigateToFeedbackDialog()
            }
        }
    }

    private fun initCategoryTextViewClickListener() {
        with(binding) {
            tvMyFeedbackGrammarCategory.setOnClickListener {
                tvMyFeedbackGrammarCategory.isSelected = !tvMyFeedbackGrammarCategory.isSelected
                navigateToFeedbackDialog()
            }
        }
    }

    private fun initMyFeedbackAdapter() {
        with(binding.rvMyFeedbackGrammar) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyGrammarAdapter(mockDataList)
        }
    }

    private fun navigateToFeedbackDialog() {
        findNavController().navigate(R.id.action_my_page_feedback_to_my_page_feedback_dialog)
    }

    companion object {
        private val mockDataList = listOf(
            Grammar(
                "take",
                "took",
                "과거의 사실을 얘기할 때는 동사의 과거형을 사용"
            ),
            Grammar(
                "that",
                "which",
                "전치사 + that은 불가능하다"
            ),
            Grammar(
                "that",
                "which",
                "comma 뒤에는 계속적 용법 사용 불가능"
            ),
        )
    }
}