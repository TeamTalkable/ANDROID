package com.talkable.presentation.onboarding.model

import android.os.Parcelable
import com.talkable.presentation.onboarding.type.DayOfWeek
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val name: String,
    var alarmAmPm: Int? = null,
    var alarmTimeHour: Int? = null,
    var alarmTimeMin: Int? = null,
    val alarmDay: MutableMap<DayOfWeek, Boolean> = DayOfWeek.values().associateWith { false }
        .toMutableMap()
) : Parcelable