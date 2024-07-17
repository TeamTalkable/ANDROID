package com.talkable.presentation.onboarding

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentSettingAlarmBinding

class AlarmSettingFragment :
    BindingFragment<FragmentSettingAlarmBinding>(R.layout.fragment_setting_alarm) {
    override fun initView() {
        initCompleteBtnClickListener()
    }

    private fun initCompleteBtnClickListener() {
        binding.btnSettingAlarm.setOnClickListener {
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() =
        findNavController().navigate(R.id.action_alarmSetting_to_home)
}