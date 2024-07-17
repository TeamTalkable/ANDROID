package com.talkable.presentation.onboarding.model

data class User(
    val name: String,
    val alarmTimeHour: Int? = null,
    val alarmTimeMin: Int? = null,
    val alarmDay: String? = null,
)