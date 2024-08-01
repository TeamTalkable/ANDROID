package com.talkable.presentation.onboarding

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.DialogKey
import com.talkable.databinding.FragmentSettingAlarmBinding
import com.talkable.presentation.onboarding.StartSettingFragment.Companion.mockData

class AlarmSettingFragment :
    BindingFragment<FragmentSettingAlarmBinding>(R.layout.fragment_setting_alarm) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()
    override fun initView() {
        initAlarmSettingClickListener()
        initCompleteBtnClickListener()
        observeDialogClosed()
    }

    private fun observeDialogClosed() {
        onboardingViewModel.dialogClosed.observe(viewLifecycleOwner) { isClosed ->
            if (isClosed) {
                updateAlarmTimeTextView()
                onboardingViewModel.resetDialogClose()
            }
        }
    }

    private fun updateAlarmTimeTextView() = with(binding) {
        val hour = mockData.alarmTimeHour?.let {
            if (mockData.alarmAmPm == 1) it + 12 else it
        }
        tvSettingAlarmHour.text =
            getString(R.string.label_alarm_setting_hour_input, hour ?: 0)
        binding.tvSettingAlarmMin.text =
            getString(R.string.label_alarm_setting_minute_input, mockData.alarmTimeMin ?: 0)
    }

    private fun initAlarmSettingClickListener() {
        binding.groupSettingAlarmTime.setOnClickListener {
            showAlarmSettingDialog()
        }
    }

    private fun showAlarmSettingDialog() = AlarmSettingDialog.createNewInstance().show(
        childFragmentManager, DialogKey.ALARM_SETTING_DIALOG
    )

    private fun initCompleteBtnClickListener() {
        binding.btnSettingAlarm.setOnClickListener {
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() =
        findNavController().navigate(R.id.action_alarmSetting_to_home)
}