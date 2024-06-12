package com.talkable.presentation.mypage.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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

@Parcelize
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
) : Parcelable

data class ChartDetail(
    val totalPercent: String,
    val grammarDetail: String,
    val expressionDetail: String,
    val pronunciationDetail: String,
    val listeningDetail: String,
)

data class BarChart(
    val id: Int,
    val date: String,
    val studyTime: Int
)