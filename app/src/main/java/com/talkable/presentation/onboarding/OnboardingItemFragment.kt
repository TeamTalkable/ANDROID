package com.talkable.presentation.onboarding

import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.ItemOnboardingBinding
import com.talkable.presentation.onboarding.model.Onboarding

class OnboardingItemFragment(private val model: Onboarding) :
    BindingFragment<ItemOnboardingBinding>(R.layout.item_onboarding) {
    override fun initView() {
        binding.model = model
    }
}