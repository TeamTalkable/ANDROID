package com.talkable.presentation.onboarding

import android.widget.NumberPicker
import androidx.fragment.app.activityViewModels
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.DialogSelectAlarmTimeBinding
import com.talkable.presentation.onboarding.StartSettingFragment.Companion.mockData
import com.talkable.presentation.onboarding.type.DayOfWeek

class AlarmSettingDialog :
    BindingDialogFragment<DialogSelectAlarmTimeBinding>(R.layout.dialog_select_alarm_time) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun initView() {
        setPickerValue(binding.pickerSelectAlarmTime, 0, meridiemArr)
        setPickerValue(binding.pickerSelectAlarmTimeHour, 1, hoursArr)
        setPickerValue(binding.pickerSelectAlarmMinute, 0, minutesArr)
        setDefaultPickerTime()
        initDayOfWeekClickListener()
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 20f)
    }

    private fun setPickerValue(picker: NumberPicker, min: Int, array: Array<String>) =
        with(picker) {
            minValue = min
            maxValue = if (array.contentEquals(hoursArr)) 12 else array.size - 1
            displayedValues = array
        }

    private fun mapDayTextView() = mapOf(
        DayOfWeek.SUN to binding.tvSelectAlarmTimeSun,
        DayOfWeek.MON to binding.tvSelectAlarmTimeMon,
        DayOfWeek.TUE to binding.tvSelectAlarmTimeTue,
        DayOfWeek.WED to binding.tvSelectAlarmTimeWed,
        DayOfWeek.THU to binding.tvSelectAlarmTimeThu,
        DayOfWeek.FRI to binding.tvSelectAlarmTimeFri,
        DayOfWeek.SAT to binding.tvSelectAlarmTimeSat
    )

    private fun setDefaultPickerTime() {
        binding.pickerSelectAlarmTime.value = mockData.alarmAmPm ?: 0
        binding.pickerSelectAlarmTimeHour.value = mockData.alarmTimeHour ?: 1
        binding.pickerSelectAlarmMinute.value = mockData.alarmTimeMin ?: 0

        mapDayTextView().forEach { (dayOfWeek, textView) ->
            textView.isSelected = mockData.alarmDay.getOrDefault(dayOfWeek, false)
        }
    }

    private fun initDayOfWeekClickListener() {
        mapDayTextView().forEach { (dayOfWeek, textView) ->
            textView.setOnClickListener {
                mockData.alarmDay[dayOfWeek] = !mockData.alarmDay.getOrDefault(dayOfWeek, false)
                textView.isSelected = !textView.isSelected
            }
        }
    }

    override fun onDestroyView() {
        loadAlarmTime()
        onboardingViewModel.closeDialog()
        super.onDestroyView()
    }

    private fun loadAlarmTime() {
        mockData.alarmAmPm = binding.pickerSelectAlarmTime.value
        mockData.alarmTimeHour = binding.pickerSelectAlarmTimeHour.value
        mockData.alarmTimeMin = binding.pickerSelectAlarmMinute.value
    }

    companion object {
        private val meridiemArr = arrayOf("오전", "오후")
        private val hoursArr = Array(12) { (it + 1).toString() }
        private val minutesArr =
            Array(10) { i -> String.format("%02d", i) } + Array(50) { (it + 10).toString() }

        fun createNewInstance() = AlarmSettingDialog()
    }
}