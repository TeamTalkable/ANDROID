package com.talkable.presentation.feedback.today

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.databinding.ItemTodayFeedbackExpressionBinding
import com.talkable.databinding.ItemTodayFeedbackGrammarBinding
import com.talkable.databinding.ItemTodayFeedbackPronunciationBinding
import com.talkable.presentation.feedback.today.model.TodayFeedback

class FeedbackExpressionViewHolder(private val binding: ItemTodayFeedbackExpressionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(feedbackData: TodayFeedback.Expression, isLastItem: Boolean, isOnlyItem: Boolean) {
        binding.run {
            layoutTodayFeedbackExpression.tvTalkFeedbackLearnedExpressionEnglish.text = feedbackData.english
            layoutTodayFeedbackExpression.tvTalkFeedbackLearnedExpressionKorean.text = feedbackData.translation
            layoutTodayFeedback.tvTodayFeedbackBefore.text = feedbackData.feedbackBefore
            layoutTodayFeedback.tvTodayFeedbackAfter.text = feedbackData.feedbackAfter

            viewTodayFeedbackCircle.visibility = if (isOnlyItem) View.GONE else View.VISIBLE
            dividerTodayFeedbackExpression.visibility = if (isOnlyItem || isLastItem) View.GONE else View.VISIBLE
        }
    }
}

class FeedbackGrammarViewHolder(private val binding: ItemTodayFeedbackGrammarBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(feedbackData: TodayFeedback.Grammar, isLastItem: Boolean, isOnlyItem: Boolean) {
        binding.run {
            layoutTodayFeedbackGrammar.tvMyFeedbackGrammarWrong.text = feedbackData.wrong
            layoutTodayFeedbackGrammar.tvMyFeedbackGrammarCorrect.text = feedbackData.correct
            layoutTodayFeedbackGrammar.tvMyFeedbackGrammarReason.text = feedbackData.reason
            layoutTodayFeedback.tvTodayFeedbackBefore.text = feedbackData.feedbackBefore
            layoutTodayFeedback.tvTodayFeedbackAfter.text = feedbackData.feedbackAfter

            viewTodayFeedbackCircle.visibility = if (isOnlyItem) View.GONE else View.VISIBLE
            dividerTodayFeedbackGrammar.visibility = if (isOnlyItem || isLastItem) View.GONE else View.VISIBLE
        }
    }
}

class FeedbackPronunciationViewHolder(private val binding: ItemTodayFeedbackPronunciationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(feedbackData: TodayFeedback.Pronunciation, isLastItem: Boolean, isOnlyItem: Boolean) {
        val context = binding.root.context

        binding.run {
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationWord.text = feedbackData.word
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationWordPronun.text = context.getString(R.string.tv_today_saved_pronunciation, feedbackData.pronunciation)
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationTranslation.text = feedbackData.translation
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationSentence.text = feedbackData.sentence
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationAccuracy.text = context.getString(R.string.tv_today_saved_pronunciation_accuracy, feedbackData.accuracy)

            viewTodayFeedbackCircle.visibility = if (isOnlyItem) View.GONE else View.VISIBLE
            dividerTodayFeedbackPronunciation.visibility = if (isOnlyItem || isLastItem) View.GONE else View.VISIBLE
        }
    }
}