package com.talkable.presentation.mypage.saved

import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.DialogKey
import com.talkable.databinding.FragmentSavedBinding
import com.talkable.presentation.quiz.QuizCategoryDialog

class SavedFragment :
    BindingFragment<FragmentSavedBinding>(R.layout.fragment_saved) {
    override fun initView() {
        initBackBtnClickListener()
        initMyFeedbackViewPagerAdapter()
        initQuizBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.layoutMyPageSavedAppBar.ibMyPageBack.setOnClickListener {
            navigateToBack()
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    private fun initMyFeedbackViewPagerAdapter() {
        binding.vpMyPageSaved.adapter = SavedViewPagerAdapter(this, SavedCategory.values())
        TabLayoutMediator(binding.tlMyPageSaved, binding.vpMyPageSaved) { tab, position ->
            tab.text = SavedCategory.values()[position].label
        }.attach()
    }

    private fun initQuizBtnClickListener() {
        binding.layoutMyPageSavedAppBar.ibMyPageWrite.setOnClickListener {
            QuizCategoryDialog.createNewInstance().show(
                childFragmentManager, DialogKey.QUIZ_DIALOG
            )
        }
    }
}