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
        initParticipationAdapter()
        initRecruitmentAdapter()
        initNavigateChallengeRecruitment()
        setChallengeUserRanking()
        setChallengeRanking()
        initRankingAdapter()
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

    private fun setChallengeRanking() {
        with(binding.includeLayoutChallengeRanking) {
            val rankingViews = listOf(
                Pair(tvRanking1UsreName, tvRanking1Time),
                Pair(tvRanking2UsreName, tvRanking2Time),
                Pair(tvRanking3UsreName, tvRanking3Time)
            )

            rankingViews.zip(rankingMockData).forEach { (views, ranking) ->
                views.first.text = ranking.name
                views.second.text = ranking.time
            }
        }
    }

    private fun initRankingAdapter() {
        with(binding.rvChallengeRanking) {
            layoutManager = LinearLayoutManager(context)
            adapter = RankingAdapter().apply {
                // 전체 리스트에서 하위 2개의 항목만 선택
                val sublist = if (rankingMockData.size > 2) {
                    rankingMockData.takeLast(2)
                } else {
                    rankingMockData
                }
                submitList(sublist)
            }
            addItemDecoration(RankingItemDecorator(requireActivity()))
        }
    }

    private fun setChallengeUserRanking() {
        with(binding) {
            tvChallengeUserRanking.text = userRankingMockData.rank
            tvChallengeUserName.text = userRankingMockData.name
            tvChallengeUserTime.text = userRankingMockData.time
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
        Ranking(rank = "1위", name = "김민지", time = "190분"),
        Ranking(rank = "2위", name = "최우지", time = "164분"),
        Ranking(rank = "3위", name = "박가은", time = "70분"),
        Ranking(rank = "4위", name = "김은서", time = "50분"),
        Ranking(rank = "5위", name = "박혜림", time = "40분"),
    )

    private val userRankingMockData = Ranking(
        rank = "115위", name = "김지은", time = "15분"
    )
}