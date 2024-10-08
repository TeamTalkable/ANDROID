package com.talkable.presentation

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.talkable.R
import com.talkable.core.util.context.colorOf

class FeedbackTextColor(val context: Context) {
    fun setAfterAnswerTextColor(fullText: String, partsText: List<String>): SpannableString {
        val spannableString = SpannableString(fullText)

        partsText.forEach { part ->
            val startIndex = fullText.indexOf(part)
            if (startIndex != -1) {
                val endIndex = startIndex + part.length
                val span = ForegroundColorSpan(context.colorOf(R.color.main_4))
                spannableString.setSpan(
                    span,
                    startIndex,
                    endIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val boldSpan = StyleSpan(Typeface.BOLD)
                spannableString.setSpan(
                    boldSpan,
                    startIndex,
                    endIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        return spannableString
    }
}