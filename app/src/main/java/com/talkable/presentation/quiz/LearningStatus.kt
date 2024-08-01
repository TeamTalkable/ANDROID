package com.talkable.presentation.quiz

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.talkable.R

enum class LearningStatus(
    @StringRes val label: Int,
    @ColorRes val backgroundColor: Int,
    @ColorRes val textColor: Int
) {
    DIFFICULT(R.string.tv_saved_difficult, R.color.main, R.color.white),
    MEMORIZED(R.string.tv_saved_memorized, R.color.gray, R.color.white),
    MEMORIZING(R.string.tv_saved_memorizing, R.color.main_1, R.color.font)
}