package com.talkable.presentation.mypage.feedback

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFeedbackViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3 // 탭 개수
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyFeedbackPronunciationFragment()
            1 -> MyFeedbackGrammarFragment()
            2 -> MyFeedbackExpressionFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
