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
        initNavigateFlashcardsBtnClickListener()
        initNavigateAutoBtnClickListener()
        initNavigateSavedBtnClickListener()
        initNavigateFeedbackBtnClickListener()
    }

    private fun initNavigateMeaningBtnClickListener() {
        binding.btnReviewMeaning.setOnClickListener {
            navigateToMeaning()
        }
    }

    private fun navigateToMeaning() =
        findNavController().navigate(R.id.action_review_to_quiz_spelling_meaning)

    private fun initNavigateFlashcardsBtnClickListener() {
        binding.btnReviewFlashcards.setOnClickListener {
            navigateToFlashcards()
        }
    }

    private fun navigateToFlashcards() =
        findNavController().navigate(R.id.action_review_to_quiz_flash)

    private fun initNavigateAutoBtnClickListener() {
        binding.btnReviewAuto.setOnClickListener {
            navigateToAuto()
        }
    }

    private fun navigateToAuto() =
        findNavController().navigate(R.id.action_review_to_quiz_auto)

    private fun initNavigateSavedBtnClickListener() {
        binding.includeReviewSaved.layoutReviewSaved.setOnClickListener {
            navigateToSaved()
        }
    }

    private fun navigateToSaved() =
        findNavController().navigate(R.id.action_review_to_saved)

    private fun initNavigateFeedbackBtnClickListener() {
        binding.includeReviewFeedback.layoutReviewSaved.setOnClickListener {
            navigateToFeedback()
        }
    }

    private fun navigateToFeedback() =
        findNavController().navigate(R.id.action_review_to_feedback)
}