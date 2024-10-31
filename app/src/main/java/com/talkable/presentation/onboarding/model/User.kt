package com.talkable.presentation.onboarding.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val name: String,
    var alarmAmPm: Int? = null,
    var alarmTimeHour: Int? = null,
    var alarmTimeMin: Int? = null,
) : Parcelable