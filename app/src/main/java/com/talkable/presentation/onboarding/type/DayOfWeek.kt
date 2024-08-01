package com.talkable.presentation.onboarding.type

import androidx.annotation.StringRes
import com.talkable.R

enum class DayOfWeek(@StringRes day: Int) {
    SUN(R.string.label_alarm_day_sun),
    MON(R.string.label_alarm_day_mon),
    TUE(R.string.label_alarm_day_tue),
    WED(R.string.label_alarm_day_wed),
    THU(R.string.label_alarm_day_thu),
    FRI(R.string.label_alarm_day_fri),
    SAT(R.string.label_alarm_day_sat),
}