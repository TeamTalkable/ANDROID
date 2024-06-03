package com.talkable.presentation.mypage.model

data class MyPageModel(
    val userId: Int,
    val userNickName: String,
    val userProfileImage: String,
    val isTodayTalk: Boolean,
    val talkTime: String,
    val saveCount: String,
    val feedbackCount: String,
    val alarmTime: String,
    val alarmDate: String,
    val chartData: Chart,
)

data class Chart(
    val beforeGrammarMeasure: Int = 0,
    val afterGrammarMeasure: Int = 0,
    val beforeExpressionMeasure: Int = 0,
    val afterExpressionMeasure: Int = 0,
    val beforePronunciationMeasure: Int = 0,
    val afterPronunciationMeasure: Int = 0,
    val beforeListeningMeasure: Int = 0,
    val afterListeningMeasure: Int = 0,
    val isEmpty: Boolean = true,
)