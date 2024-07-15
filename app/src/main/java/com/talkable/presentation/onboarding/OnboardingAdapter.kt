package com.talkable.presentation.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talkable.presentation.onboarding.model.Onboarding

class OnboardingAdapter(fragment: Fragment, private val dataList: List<Onboarding>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return OnboardingItemFragment(dataList.get(position))
    }
}