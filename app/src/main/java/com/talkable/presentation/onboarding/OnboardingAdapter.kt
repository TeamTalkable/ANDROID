package com.talkable.presentation.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talkable.presentation.onboarding.type.Onboarding

class OnboardingAdapter(fragment: Fragment, private val dataList: Array<Onboarding>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = dataList.size

    override fun createFragment(position: Int): Fragment {
        return OnboardingItemFragment.createNewInstance(dataList[position])
    }
}