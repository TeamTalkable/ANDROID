package com.talkable.presentation.review

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentReviewBinding

class ReviewFragment : BindingFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    override fun initView() {
        statusBarColorOf(R.color.main_2)
        initNavigateMeaningBtnClickListener()
    }

    private fun initNavigateMeaningBtnClickListener() {
        binding.btnReviewMeaning.setOnClickListener {
            navigateToMeaning()
        }
    }

    private fun navigateToMeaning() =
        findNavController().navigate(R.id.action_review_to_quiz_spelling_meaning)
}