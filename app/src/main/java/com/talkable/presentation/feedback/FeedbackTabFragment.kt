package com.talkable.presentation.feedback

import androidx.core.view.isInvisible
import com.google.android.material.tabs.TabLayoutMediator
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.colorOf
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentTodaySavedBinding

class FeedbackTabFragment :
    BindingFragment<FragmentTodaySavedBinding>(R.layout.fragment_today_saved) {

    override fun initView() {
        binding.layoutTodaySavedAppBar.layout.visible(false)
        binding.tlTodaySaved.setBackgroundColor(colorOf(R.color.white))
        initMyFeedbackViewPagerAdapter()
    }

    private fun initMyFeedbackViewPagerAdapter() {
        binding.vpTodaySaved.adapter =
            FeedbackTabAdapter(this, FeedbackTab.entries.toTypedArray())
        TabLayoutMediator(binding.tlTodaySaved, binding.vpTodaySaved) { tab, position ->
            tab.text = FeedbackTab.entries[position].label

        }.attach()
        binding.vpTodaySaved.setCurrentItem(1, false)
    }
}

enum class FeedbackTab(val label: String) {
    GRAMMAR("표현/문법 피드백"),
    PRONUNCIATION("발음 피드백")
}