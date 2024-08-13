package com.talkable.presentation.home

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentHomeBinding
import com.talkable.presentation.quiz.TodayQuizDialog

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        initQuizBtnClickListener()
        initViewPagerAdapter()
        initStartBtnClickListener()
        setLearningTextView()
    }

    private fun initQuizBtnClickListener() {
        binding.icHomeQuiz.setOnClickListener {
            TodayQuizDialog.createNewInstance(3, childFragmentManager)
        }
    }

    private fun setLearningTextView() {
        with(binding.includeLayoutTalkGuide) {
            tvHomeLearningTime.text = getString(R.string.tv_home_learning_time, 20)
            tvHomeLearningStorage.text = getString(R.string.tv_home_learning_storage, 5)
            tvHomeLearningFeedback.text = getString(R.string.tv_home_learning_feedback, 3)
        }
    }

    private fun initStartBtnClickListener() {
        binding.btnHomeTalkStart.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_talk)
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