package com.talkable.presentation.challenge

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
    }

    private fun setChallengeTextView() {
        with(binding) {
            tvChallengeRanking.text = getString(R.string.tv_challenge_ranking, 6)
        }
    }

    // 참여중인 챌린지
    private fun initParticipationAdapter() {
        with(binding.viewpagerChallengeParticipation) {
            offscreenPageLimit = 2 // 미리 로드할 페이지 수
            setPadding(0, 0, 18, 0)
            adapter = ChallengeParticipationAdapter(participationList)
        }
    }

    // 모집중인 챌린지
    private fun initRecruitmentAdapter() {
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin) // 페이지끼리 간격
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth) // 페이지 보이는 정도
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 가로 길이
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        with(binding.viewpagerChallengeRecruitment) {
            setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
            offscreenPageLimit = 2
            adapter = ChallengeRecruitmentAdapter(challengeList)
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
            image = R.drawable.img_challenge_fertilizer,
            date = "06.00 ~ 06.03",
            title = "3일동안 10분 이상\n매일 학습하기",
            progress = 90
        ),
        Participation(
            image = R.drawable.img_challenge_shovel,
            date = "06.00 ~ 06.07",
            title = "7일동안 20분 이상\n매일 학습하기",
            progress = 50
        ),
        Participation(
            image = R.drawable.img_challenge_medicine,
            date = "06.00 ~ 06.10",
            title = "10일동안 30분 이상\n매일 학습하기",
            progress = 10
        ),
    )

    private val challengeList = listOf(
        Recruitment(
            image = R.drawable.img_challenge_sun,
            date = "06.00 ~ 06.03",
            title = "3일동안 10분 이상\n매일 학습하기",
        ),
        Recruitment(
            image = R.drawable.img_challenge_wind,
            date = "06.00 ~ 06.07",
            title = "7일동안 20분 이상\n매일 학습하기",
        ),
        Recruitment(
            image = R.drawable.img_challenge_water,
            date = "06.00 ~ 06.10",
            title = "10일동안 30분 이상\n매일 학습하기",
        )
    )

    private val rankingMockData = listOf(
        Ranking(rank = "4위", name = "김은서", time = "50분"),
        Ranking(rank = "5위", name = "박혜림", time = "40분"),
    )
}