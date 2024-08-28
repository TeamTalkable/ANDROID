package com.talkable.presentation.onboarding

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.DialogKey
import com.talkable.databinding.FragmentSettingAlarmBinding
import com.talkable.presentation.onboarding.StartSettingFragment.Companion.mockData
import timber.log.Timber

class AlarmSettingFragment :
    BindingFragment<FragmentSettingAlarmBinding>(R.layout.fragment_setting_alarm) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()
    override fun initView() {
        initAlarmSettingClickListener()
        initCompleteBtnClickListener()
        observeDialogClosed()
        initPushAlarmPermissionAlert()
    }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    private fun initPushAlarmPermissionAlert() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionList = Manifest.permission.POST_NOTIFICATIONS
            requestPermission.launch(permissionList)
        } else {
            handlePushAlarmPermissionGranted()
        }
    }

    private fun handlePushAlarmPermissionGranted() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.tag("fcm").d("fcm token: $task.result")
                } else {
                    Timber.d(task.exception)
                    return@OnCompleteListener
                }
            }
        )
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