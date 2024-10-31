package com.talkable.presentation.feedback

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FeedbackTabAdapter(
    fragment: Fragment,
    private val categories: Array<FeedbackTab>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (categories[position]) {
            FeedbackTab.GRAMMAR -> FeedbackExpressionFragment.newInstance()
            else -> FeedbackPronunciationFragment.newInstance()
        }
    }
}