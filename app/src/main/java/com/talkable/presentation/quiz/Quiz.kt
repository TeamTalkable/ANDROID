package com.talkable.presentation.quiz

import androidx.annotation.StringRes
import com.talkable.R

enum class Quiz(@StringRes val title: Int, @StringRes val description: Int) {
    SPELLING(R.string.label_quiz_spelling, R.string.description_quiz_spelling),
    MEANING(R.string.label_quiz_meaning, R.string.description_quiz_meaning),
    FLASH(R.string.label_quiz_flash, R.string.description_quiz_flash),
    AUTO(R.string.label_quiz_auto, R.string.description_quiz_auto)
}