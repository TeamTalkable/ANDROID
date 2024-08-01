package com.talkable.presentation.onboarding

import android.os.Build
import android.os.Bundle
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.databinding.ItemOnboardingBinding
import com.talkable.presentation.onboarding.type.Onboarding

class OnboardingItemFragment() :
    BindingFragment<ItemOnboardingBinding>(R.layout.item_onboarding) {
    override fun initView() {
        binding.model = getOnboardingData()
    }

    private fun getOnboardingData() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getSerializable(Key.ONBOARDING_KEY, Onboarding::class.java)
    } else {
        arguments?.getSerializable(Key.ONBOARDING_KEY) as? Onboarding
    }

    companion object {
        fun createNewInstance(data: Onboarding): OnboardingItemFragment {
            return OnboardingItemFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Key.ONBOARDING_KEY, data)
                }
            }
        }
    }
}