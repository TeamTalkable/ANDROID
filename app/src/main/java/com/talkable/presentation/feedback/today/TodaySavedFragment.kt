package com.talkable.presentation.feedback.today

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentTodaySavedBinding

class TodaySavedFragment :
    BindingFragment<FragmentTodaySavedBinding>(R.layout.fragment_today_saved) {

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initSetToolbarColor()
        initBackBtnClickListener()
        initMyFeedbackViewPagerAdapter()
    }

    private fun initSetToolbarColor() {
        val color = ContextCompat.getColor(requireContext(), R.color.main_3)
        binding.layoutTodaySavedAppBar.root.setBackgroundColor(color)
    }

    private fun initBackBtnClickListener() {
        binding.layoutTodaySavedAppBar.btnAppBarBack.setOnClickListener {
            navigateToBack()
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    private fun initMyFeedbackViewPagerAdapter() {
        binding.vpTodaySaved.adapter =
            TodaySavedPagerAdapter(this, TodaySavedCategory.values())
        TabLayoutMediator(binding.tlTodaySaved, binding.vpTodaySaved) { tab, position ->
            tab.text = TodaySavedCategory.values()[position].label
        }.attach()
    }
}