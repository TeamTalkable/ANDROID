package com.talkable.presentation.mypage.feedback

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentMyFeedbackExpressionBinding
import com.talkable.presentation.mypage.feedback.data.Expression

class MyFeedbackExpressionFragment :
    BindingFragment<FragmentMyFeedbackExpressionBinding>(R.layout.fragment_my_feedback_expression) {
    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initLatestTextViewClickListener()
        initCategoryTextViewClickListener()
        initMyFeedbackAdapter()
    }

    private fun initLatestTextViewClickListener() {
        with(binding) {
            tvMyFeedbackExpressionLatest.setOnClickListener {
                tvMyFeedbackExpressionLatest.isSelected = !tvMyFeedbackExpressionLatest.isSelected
                navigateToFeedbackDialog()
            }
        }
    }

    private fun initCategoryTextViewClickListener() {
        with(binding) {
            tvMyFeedbackExpressionCategory.setOnClickListener {
                tvMyFeedbackExpressionCategory.isSelected =
                    !tvMyFeedbackExpressionCategory.isSelected
                navigateToFeedbackDialog()
            }
        }
    }

    private fun initMyFeedbackAdapter() {
        with(binding.rvMyFeedbackExpression) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyExpressionAdapter(mockDataList)
            addItemDecoration(MyFeedbackDecorator(requireContext()))
        }
    }

    private fun navigateToFeedbackDialog() {
        findNavController().navigate(R.id.action_my_page_feedback_to_my_page_feedback_dialog)
    }

    companion object {
        private val mockDataList = listOf(
            Expression(
                "the proximity to Central Park",
                "‘중앙공원과의 가까운 거리’ 를 의미하는 표현"
            ),
            Expression(
                "which allows me to enjoy nature",
                "이로 인해 자연을 즐길 수 있다는 점을 강조하는 표현"
            ),
            Expression(
                "enjoy nature",
                "‘자연을 즐기다'라는 자연스러운 표현"
            ),
            Expression(
                "the proximity to Central Park",
                "‘중앙공원과의 가까운 거리’ 를 의미하는 표현"
            ),
            Expression(
                "which allows me to enjoy nature",
                "이로 인해 자연을 즐길 수 있다는 점을 강조하는 표현"
            ),
            Expression(
                "enjoy nature",
                "‘자연을 즐기다'라는 자연스러운 표현"
            )
        )
    }
}