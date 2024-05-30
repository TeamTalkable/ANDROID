package com.talkable.presentation.mypage.model

data class MyPageModel(
    val userId: Int,
    val userNickName: String,
    val userProfileImage: String,
    val isTodayTalk: Boolean,
    val talkTime: String,
    val saveCount : String,
    val feedbackCount : String,
    )