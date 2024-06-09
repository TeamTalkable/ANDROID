package com.talkable.presentation.home

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentHomeBinding

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        initViewPagerAdapter()
        initStartBtnClickListener()
    }

    private fun initStartBtnClickListener() {
        binding.btnHomeTalkStart.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_talk)
        }
    }

    private fun initViewPagerAdapter() {
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin) // 페이지끼리 간격
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth) // 페이지 보이는 정도
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 가로 길이
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.viewpagerHomeChallenge.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        binding.viewpagerHomeChallenge.adapter = HomeChallengeAdapter(challengeList)
    }

    private val challengeList = listOf(
        R.drawable.img_home_challenge_done,
        R.drawable.img_home_no_challenge,
        R.drawable.img_home_challenge_progress,
    )
}