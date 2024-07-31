package com.talkable.presentation.mypage.saved

import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentSavedBinding

class SavedFragment :
    BindingFragment<FragmentSavedBinding>(R.layout.fragment_saved) {
    override fun initView() {
        initBackBtnClickListener()
        initMyFeedbackViewPagerAdapter()
    }

    private fun initBackBtnClickListener() {
        binding.layoutMyPageSavedAppBar.ibMyPageBack.setOnClickListener {
            navigateToBack()
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    private fun initMyFeedbackViewPagerAdapter() {
        binding.vpMyPageSaved.adapter = SavedViewPagerAdapter(this)

        TabLayoutMediator(binding.tlMyPageSaved, binding.vpMyPageSaved) { tab, position ->
            tab.text = when (position) {
                CATEGORY_WORD -> getString(R.string.tv_my_feedback_category_word)
                CATEGORY_SYNTAX -> getString(R.string.tv_saved_syntax)
                else -> null
            }
        }.attach()
    }

    companion object {
        const val CATEGORY_WORD = 0
        const val CATEGORY_SYNTAX = 1
        const val NO_CHIP_SELECTED = -1
    }
}