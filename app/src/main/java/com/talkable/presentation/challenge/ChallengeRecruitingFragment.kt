package com.talkable.presentation.challenge

import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentChallengeRecruitingBinding

class ChallengeRecruitingFragment :
    BindingFragment<FragmentChallengeRecruitingBinding>(R.layout.fragment_challenge_recruiting) {

    private lateinit var challengeRecruitingAdapter: ChallengeRecruitingAdapter

    override fun initView() {
        statusBarColorOf(R.color.white)
        initChallengeRecruitingAdapter()
    }

    private fun initChallengeRecruitingAdapter() {
        challengeRecruitingAdapter = ChallengeRecruitingAdapter()
        with(binding.rvChallenge) {
            adapter = challengeRecruitingAdapter
        }
        challengeRecruitingAdapter.submitList(challengeList)
    }

    // 더미 데이터
    private val challengeList = listOf(
        Recruiting(
            image = R.drawable.ic_my_flower_item_sun,
            date = "06.01 ~ 06.08",
            title = "하루에 15분 이상 매일 학습하기",
            count = "👥 32"
        ),
        Recruiting(
            image = R.drawable.ic_my_flower_item_wind,
            date = "06.10 ~ 06.17",
            title = "하루에 30분 이상 매일 학습하기",
            count = "👥 21"
        ),
        Recruiting(
            image = R.drawable.ic_my_flower_item_water,
            date = "06.18 ~ 06.25",
            title = "하루에 50분 이상 매일 학습하기",
            count = "👥 18"
        )
    )
}