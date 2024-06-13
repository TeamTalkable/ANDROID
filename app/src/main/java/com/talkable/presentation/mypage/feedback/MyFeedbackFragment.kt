package com.talkable.presentation.mypage.feedback

import com.google.android.material.tabs.TabLayoutMediator
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentMyPageFeedbackBinding

class MyFeedbackFragment :
    BindingFragment<FragmentMyPageFeedbackBinding>(R.layout.fragment_my_page_feedback) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        initMyFeedbackViewPagerAdapter()
    }

    private fun initMyFeedbackViewPagerAdapter() {
        binding.vpMyPageFeedback.adapter = MyFeedbackViewPagerAdapter(this)

        TabLayoutMediator(binding.tlMyPageFeedback, binding.vpMyPageFeedback) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tv_my_page_chart_label_pronunciation)
                1 -> getString(R.string.tv_my_page_chart_label_grammar)
                2 -> getString(R.string.tv_my_page_feedback_expression)
                else -> null
            }
        }.attach()
    }
}