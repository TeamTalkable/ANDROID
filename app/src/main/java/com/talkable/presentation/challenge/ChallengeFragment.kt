package com.talkable.presentation.challenge

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentChallengeBinding

class ChallengeFragment : BindingFragment<FragmentChallengeBinding>(R.layout.fragment_challenge) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        setChallengeTextView()
        initRankingAdapter()
        initParticipationAdapter()
        initRecruitmentAdapter()
        initNavigateChallengeRecruitment()
    }

    private fun initNavigateChallengeRecruitment() {
        binding.btnChallengeRecruitment.setOnClickListener {
            navigateToChallengeRecruitment()
        }
    }

    private fun navigateToChallengeRecruitment() =
        findNavController().navigate(R.id.action_challenge_to_challenge_recruitment)


    private fun setChallengeTextView() {
        with(binding) {
            tvChallengeRanking.text = getString(R.string.tv_challenge_ranking, 6)
        }
    }

    // 참여중인 챌린지
    private fun initParticipationAdapter() {
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.participation_margin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.participation_width)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        with(binding.viewpagerChallengeParticipation) {
            offscreenPageLimit = 2
            adapter = ChallengeParticipationAdapter(participationList)
            setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
        }
    }

    // 모집중인 챌린지
    private fun initRecruitmentAdapter() {
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.recruitment_margin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.recruitment_width)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        with(binding.viewpagerChallengeRecruitment) {
            setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
            offscreenPageLimit = 2

            adapter = ChallengeRecruitmentAdapter(challengeList) { recruitment ->
                ChallengeDialog(requireContext(), recruitment).show()
            }
        }
    }

    private fun initRankingAdapter() {
        with(binding.rvChallengeRanking) {
            layoutManager = LinearLayoutManager(context)
            adapter = RankingAdapter().apply {
                submitList(rankingMockData)
            }
            addItemDecoration(RankingItemDecorator(requireActivity()))
        }
    }

    // 더미 데이터
    private val participationList = listOf(
        Participation(
            image = R.drawable.ic_my_flower_item_compost,
            date = "06.01 ~ 06.04",
            title = "3일동안 10분 이상\n매일 학습하기",
            progress = 90
        ),
        Participation(
            image = R.drawable.ic_my_flower_item_shovel,
            date = "06.05 ~ 06.12",
            title = "7일동안 20분 이상\n매일 학습하기",
            progress = 50
        ),
        Participation(
            image = R.drawable.ic_my_flower_item_medicine,
            date = "06.10 ~ 06.20",
            title = "10일동안 30분 이상\n매일 학습하기",
            progress = 10
        ),
    )

    private val challengeList = listOf(
        Recruitment(
            image = R.drawable.ic_my_flower_item_sun,
            date = "06.01 ~ 06.08",
            title = "하루에 15분 이상 \n매일 학습하기",
        ),
        Recruitment(
            image = R.drawable.ic_my_flower_item_wind,
            date = "06.10 ~ 06.17",
            title = "하루에 30분 이상 \n매일 학습하기",
        ),
        Recruitment(
            image = R.drawable.ic_my_flower_item_water,
            date = "06.18 ~ 06.25",
            title = "하루에 50분 이상 \n매일 학습하기",
        )
    )

    private val rankingMockData = listOf(
        Ranking(rank = "4위", name = "김은서", time = "50분"),
        Ranking(rank = "5위", name = "박혜림", time = "40분"),
    )
}