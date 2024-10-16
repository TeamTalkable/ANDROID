package com.talkable.presentation.onboarding

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentOnboardingBinding
import com.talkable.presentation.onboarding.type.Onboarding

class OnboardingFragment() :
    BindingFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        initOnboardingAdapter()
        initStartBtnClickListener()
    }

    private fun initOnboardingAdapter() {
        binding.vpOnboarding.adapter = OnboardingAdapter(this, Onboarding.values())
        binding.tlOnboardingIndicator.attachTo(binding.vpOnboarding)
    }

    private fun initStartBtnClickListener() {
        binding.layoutOnboardingStartBtn.setOnClickListener {
            findNavController().navigate(R.id.action_onboarding_to_login)
        }
    }
}