package com.talkable.presentation.review

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key.QUIZ_KEY
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentReviewBinding
import com.talkable.presentation.mypage.saved.SavedWordAdapter
import com.talkable.presentation.quiz.Quiz
import com.talkable.presentation.review.model.Saved
import com.talkable.presentation.review.model.TalkSavedModel
import com.talkable.presentation.review.model.TodaySavedUiState
import com.talkable.presentation.review.model.TodaySavedViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class ReviewFragment : BindingFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private val viewModel: TodaySavedViewModel by activityViewModels()
    private lateinit var savedWordAdapter: SavedWordAdapter
    private lateinit var feedbackWordAdapter: SavedWordAdapter

    override fun initView() {
        statusBarColorOf(R.color.main_2)
        collect()
        initNavigateSpellingBtnClickListener()
        initNavigateMeaningBtnClickListener()
        initNavigateFlashcardsBtnClickListener()
        initNavigateAutoBtnClickListener()
        initNavigateSavedBtnClickListener()
        initNavigateFeedbackBtnClickListener()
        initSetSavedListAdapter()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is TodaySavedUiState.Success -> {
                        val data = uiState.data
                        initGetSavedList(data)
                        initGetFeedbackList(data)
                    }
                    is TodaySavedUiState.Error -> Timber.e(uiState.errorMessage)
                    else -> Unit
                }
            }
        }
    }

    private fun initNavigateSpellingBtnClickListener() {
        binding.btnReviewSpelling.setOnClickListener {
            navigateToMeaning(Quiz.SPELLING.title)
        }
    }

    private fun initNavigateMeaningBtnClickListener() {
        binding.btnReviewMeaning.setOnClickListener {
            navigateToMeaning(Quiz.MEANING.title)
        }
    }

    private fun navigateToMeaning(type: Int) =
        findNavController().navigate(
            R.id.action_review_to_quiz_spelling_meaning,
            bundleOf(QUIZ_KEY to type)
        )

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

        feedbackWordAdapter = SavedWordAdapter()
        binding.includeReviewFeedback.rvReviewSaved.adapter = feedbackWordAdapter
    }

    private fun initGetSavedList(data: TalkSavedModel) {
        binding.includeReviewSaved.title = getString(R.string.tv_my_page_navigate_save)
        val latestWordList = data.savedWordList
            .sortedWith(compareByDescending<Saved.Word> { it.timestamp }.thenBy { it.wordEnglish })
            .shuffled()
            .take(2)
        savedWordAdapter.submitList(latestWordList)
    }

    private fun initGetFeedbackList(data: TalkSavedModel) {
        binding.includeReviewFeedback.title = getString(R.string.tv_my_feedback_title)
        val oldestWordList = data.savedWordList
            .sortedWith(compareBy<Saved.Word> { it.timestamp }.thenBy { it.wordEnglish })
            .take(2)
        feedbackWordAdapter.submitList(oldestWordList)
    }
}