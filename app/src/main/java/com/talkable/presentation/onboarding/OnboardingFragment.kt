package com.talkable.presentation.onboarding

import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentOnboardingBinding
import com.talkable.presentation.onboarding.model.Onboarding

class OnboardingFragment() :
    BindingFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        initOnboardingAdapter()
        initStartBtnClickListener()
    }

    private fun initOnboardingAdapter() {
        binding.vpOnboarding.adapter = OnboardingAdapter(this, data)
        TabLayoutMediator(
            binding.tlOnboardingIndicator, binding.vpOnboarding
        ) { tab, position ->

        }.attach()
    }

    private fun initStartBtnClickListener() {
        binding.tvOnboardingStartLabel.setOnClickListener {
            findNavController().navigate(R.id.action_onboarding_to_login)
        }
    }

    companion object {
        val data = listOf(
            Onboarding("AI와 프리토킹을 하며 학습해요.", R.drawable.img_onboarding_first),
            Onboarding("대화 중 교정 받아야할 부분에 대한\n피드백을 받을 수 있어요.", R.drawable.img_onboarding_second),
            Onboarding("실력 차트를 확인하고\n학습 기록을 살펴보아요", R.drawable.img_onboarding_third),
            Onboarding("챌린지로 학습 동기 부여를 받아요", R.drawable.img_onboarding_fourth),
            Onboarding("학습을 하며 나의 꽃을 피워보세요", R.drawable.img_onboarding_fifth),
        )
    }
}