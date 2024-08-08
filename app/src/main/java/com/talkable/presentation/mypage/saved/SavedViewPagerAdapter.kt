package com.talkable.presentation.mypage.saved

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SavedViewPagerAdapter(
    fragment: Fragment,
    private val categories: Array<SavedCategory>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (categories[position]) {
            SavedCategory.WORD -> SavedWordFragment.newInstance(categories[position])
            else -> SavedSyntaxFragment.newInstance(categories[position])
        }
    }
}