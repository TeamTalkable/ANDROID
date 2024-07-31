package com.talkable.presentation.mypage.saved

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talkable.presentation.mypage.saved.SavedFragment.Companion.CATEGORY_SYNTAX
import com.talkable.presentation.mypage.saved.SavedFragment.Companion.CATEGORY_WORD

class SavedViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            CATEGORY_WORD -> SavedWordFragment()
            CATEGORY_SYNTAX -> SavedSyntaxFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}