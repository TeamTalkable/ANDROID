package com.talkable.presentation.onboarding

import android.Manifest
import android.os.Build
import android.widget.NumberPicker
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

    override fun initView() {
        initCompleteBtnClickListener()
        initPushAlarmPermissionAlert()
        setPickerValue(binding.pickerSelectAlarmTime, 0, meridiemArr)
        setPickerValue(binding.pickerSelectAlarmTimeHour, 1,hoursArr)
        setPickerValue(binding.pickerSelectAlarmMinute, 0, minutesArr)
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

    private fun initCompleteBtnClickListener() {
        binding.btnSettingAlarm.setOnClickListener {
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() =
        findNavController().navigate(R.id.action_alarmSetting_to_home)

    private fun setPickerValue(picker: NumberPicker, min: Int, array: Array<String>) =
        with(picker) {
            minValue = min
            maxValue = if (array.contentEquals(hoursArr)) 12 else array.size - 1
            displayedValues = array
        }

    companion object {
        private val meridiemArr = arrayOf("오전", "오후")
        private val hoursArr = Array(12) { (it + 1).toString() }
        private val minutesArr =
            Array(10) { i -> String.format("%02d", i) } + Array(50) { (it + 10).toString() }
    }
}