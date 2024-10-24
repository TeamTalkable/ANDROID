package com.talkable.presentation.review

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentReviewBinding
import com.talkable.presentation.mypage.saved.SavedWordAdapter
import com.talkable.presentation.mypage.saved.model.SavedWordViewModel

class ReviewFragment : BindingFragment<FragmentReviewBinding>(R.layout.fragment_review) {
    private lateinit var savedWordAdapter: SavedWordAdapter
    private val viewModel: SavedWordViewModel by activityViewModels()

    override fun initView() {
        statusBarColorOf(R.color.main_2)
        initNavigateMeaningBtnClickListener()
        initNavigateFlashcardsBtnClickListener()
        initNavigateAutoBtnClickListener()
        initNavigateSavedBtnClickListener()
        initNavigateFeedbackBtnClickListener()
        initSetSavedListAdapter()
        initGetSavedList()
        initGetFeedbackList()
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

    private fun initSetSavedListAdapter() {
        savedWordAdapter = SavedWordAdapter()
        binding.includeReviewSaved.rvReviewSaved.adapter = savedWordAdapter
        binding.includeReviewFeedback.rvReviewSaved.adapter = savedWordAdapter
    }

    private fun initGetSavedList() {
        binding.includeReviewSaved.title = getString(R.string.tv_my_page_navigate_save)
        viewModel.savedWords.observe(viewLifecycleOwner, Observer { savedListModel ->
            // 처음 2개의 데이터만 가져옴
            val limitedList = savedListModel.savedWordList.take(2)
            savedWordAdapter.submitList(limitedList)
        })
    }

    private fun initGetFeedbackList() {
        binding.includeReviewFeedback.title = getString(R.string.tv_my_feedback_title)
        viewModel.savedWords.observe(viewLifecycleOwner, Observer { savedListModel ->
            val limitedList = savedListModel.savedWordList.take(2)
            savedWordAdapter.submitList(limitedList)
        })
    }
}