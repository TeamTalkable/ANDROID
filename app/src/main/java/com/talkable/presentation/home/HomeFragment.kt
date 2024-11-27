package com.talkable.presentation.home

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.core.view.visible
import com.talkable.data.SharedManager
import com.talkable.databinding.FragmentHomeBinding
import com.talkable.presentation.home.model.TalkSavedModel
import com.talkable.presentation.quiz.TodayQuizDialog
import com.talkable.presentation.talk.feedback.FinalFeedbackUiState
import com.talkable.presentation.talk.feedback.FinalTalkFeedbackViewModel
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import kotlinx.coroutines.launch

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: FinalTalkFeedbackViewModel by activityViewModels()
    private val savedViewModel: TodaySavedViewModel by activityViewModels()

    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.tvHomeUserName.text = "${SharedManager.getNickname()}님,"
        collectFeedback()
        collectSavedList()
        initQuizBtnClickListener()
        initViewPagerAdapter()
        initStartBtnClickListener()
        initTalkReviewBtnClickListener()
    }

    private fun collectFeedback() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FinalFeedbackUiState.Success -> {
                        setLearningTextView(uiState.data)
                        setFeedbackCount(calculateFeedbackCount(uiState.data))
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun collectSavedList() {
        viewLifeCycleScope.launch {
            savedViewModel.uiState.flowWithLifecycle(viewLifeCycle).collect() { uiState ->
                when (uiState) {
                    is TodaySavedUiState.Success -> {
                        setSavedCount(calculateSavedCount(uiState.data))
                    }

                    else -> Unit
                }
            }
        }
    }


    private fun initQuizBtnClickListener() {
        binding.icHomeQuiz.setOnClickListener {
            binding.tvHomeQuizLabel.visible(false)
            TodayQuizDialog.createNewInstance(1, childFragmentManager)
        }
    }

    private fun setLearningTextView(data: TalkFeedbackModel?) {
        with(binding.includeTalkCalendar) {
            data?.let {
                model = it
            } ?: run {
                groupHomeNoLearning.isVisible = true
                groupHomeLearning.isVisible = false
            }
        }
    }

    private fun setFeedbackCount(totalFeedbackCount: Int) {
        binding.includeTalkCalendar.tvHomeLearningFeedback.text =
            getString(R.string.tv_home_learning_feedback, totalFeedbackCount)
    }

    private fun calculateFeedbackCount(data: TalkFeedbackModel): Int {
        return data.learnedExpression.size +
                data.learnedGrammar.size +
                data.learnedPronunciation.size
    }

    private fun setSavedCount(totalSavedCount: Int) {
        binding.includeTalkCalendar.tvHomeLearningStorage.text =
            getString(R.string.tv_home_learning_storage, totalSavedCount)
    }

    private fun calculateSavedCount(data: TalkSavedModel): Int {
        return data.savedWordList.size +
                data.savedSentenceList.size
    }

    private fun initStartBtnClickListener() {
        binding.btnHomeTalkStart.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_talk)
        }
    }

    private fun initTalkReviewBtnClickListener() {
        binding.includeTalkCalendar.btnHomeLearningReview.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_talk_review)
        }
    }

    private fun initViewPagerAdapter() {
        binding.viewpagerHomeChallenge.adapter = HomeChallengeAdapter(challengeList)
    }

    private val challengeList = listOf(
        R.drawable.img_home_challenge_done,
        R.drawable.img_home_no_challenge,
        R.drawable.img_home_challenge_progress,
    )
}