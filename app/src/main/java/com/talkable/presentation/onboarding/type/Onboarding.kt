package com.talkable.presentation.onboarding.type

import com.talkable.R

enum class Onboarding(
    val title: String,
    val image: Int
) {
    AI("AI와 프리토킹을 하며 학습해요.", R.drawable.img_onboarding_first),
    FEEDBACK("대화 중 교정 받아야할 부분에 대한\n피드백을 받을 수 있어요.", R.drawable.img_onboarding_second),
    MY_PAGE("실력 차트를 확인하고\n학습 기록을 살펴보아요", R.drawable.img_onboarding_third),
    CHALLENGE("챌린지로 학습 동기 부여를 받아요", R.drawable.img_onboarding_fourth),
    MY_FLOWER("학습을 하며 나의 꽃을 피워보세요", R.drawable.img_onboarding_fifth)
}