package com.talkable.presentation.feedback.today

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TodaySavedPagerAdapter(
    fragment: Fragment,
    private val categories: Array<TodaySavedCategory>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (categories[position]) {
            TodaySavedCategory.SAVED -> TodaySavedListFragment.newInstance(categories[position])
            else -> TodayFeedbackFragment.newInstance(categories[position])
        }
    }
}