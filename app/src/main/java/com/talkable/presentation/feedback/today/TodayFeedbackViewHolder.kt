package com.talkable.presentation.feedback.today

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.databinding.ItemTodayFeedbackExpressionBinding
import com.talkable.databinding.ItemTodayFeedbackGrammarBinding
import com.talkable.databinding.ItemTodayFeedbackPronunciationBinding
import com.talkable.presentation.feedback.today.model.TodayFeedback

abstract class FeedbackViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(feedbackData: T, isLastItem: Boolean, isOnlyItem: Boolean)
    protected fun initItemViewClickListener(feedbackView: View, vararg views: View) {
        itemView.setOnClickListener {
            views.forEach { view ->
                view.visibility = if (view.visibility == View.GONE) View.VISIBLE else View.GONE
            }
            feedbackView.isSelected = !feedbackView.isSelected
        }
    }

    protected fun handleVisibility(
        isOnlyItem: Boolean,
        isLastItem: Boolean,
        circleView: View,
        dividerView: View
    ) {
        circleView.visibility = if (isOnlyItem) View.GONE else View.VISIBLE
        dividerView.visibility = if (isOnlyItem || isLastItem) View.GONE else View.VISIBLE
    }
}

class FeedbackExpressionViewHolder(private val binding: ItemTodayFeedbackExpressionBinding) :
    FeedbackViewHolder<TodayFeedback.Expression>(binding.root) {
    init {
        with(binding) {
            initItemViewClickListener(
                layoutTodayFeedbackExpression.btnTalkFeedback,
                spaceTodayFeedback,
                layoutTodayFeedback.layoutTodayFeedback
            )
        }
    }

    override fun onBind(
        feedbackData: TodayFeedback.Expression,
        isLastItem: Boolean,
        isOnlyItem: Boolean
    ) {
        with(binding) {
            layoutTodayFeedbackExpression.btnTalkFeedback.visibility = View.VISIBLE
            layoutTodayFeedbackExpression.tvTalkFeedbackLearnedExpressionEnglish.text = feedbackData.english
            layoutTodayFeedbackExpression.tvTalkFeedbackLearnedExpressionKorean.text = feedbackData.translation
            layoutTodayFeedback.tvTodayFeedbackBefore.text = feedbackData.feedbackBefore
            layoutTodayFeedback.tvTodayFeedbackAfter.text = feedbackData.feedbackAfter

            handleVisibility(
                isOnlyItem,
                isLastItem,
                viewTodayFeedbackCircle,
                dividerTodayFeedbackExpression
            )
        }
    }
}

class FeedbackGrammarViewHolder(private val binding: ItemTodayFeedbackGrammarBinding) :
    FeedbackViewHolder<TodayFeedback.Grammar>(binding.root) {
    init {
        with(binding) {
            initItemViewClickListener(
                layoutTodayFeedbackGrammar.btnTalkFeedback,
                spaceTodayFeedback,
                layoutTodayFeedback.layoutTodayFeedback
            )
        }
    }

    override fun onBind(
        feedbackData: TodayFeedback.Grammar,
        isLastItem: Boolean,
        isOnlyItem: Boolean
    ) {
        with(binding) {
            layoutTodayFeedbackGrammar.btnTalkFeedback.visibility = View.VISIBLE
            layoutTodayFeedbackGrammar.tvMyFeedbackGrammarWrong.text = feedbackData.wrong
            layoutTodayFeedbackGrammar.tvMyFeedbackGrammarCorrect.text = feedbackData.correct
            layoutTodayFeedbackGrammar.tvMyFeedbackGrammarReason.text = feedbackData.reason
            layoutTodayFeedback.tvTodayFeedbackBefore.text = feedbackData.feedbackBefore
            layoutTodayFeedback.tvTodayFeedbackAfter.text = feedbackData.feedbackAfter

            handleVisibility(
                isOnlyItem,
                isLastItem,
                viewTodayFeedbackCircle,
                dividerTodayFeedbackGrammar
            )
        }
    }
}

class FeedbackPronunciationViewHolder(private val binding: ItemTodayFeedbackPronunciationBinding) :
    FeedbackViewHolder<TodayFeedback.Pronunciation>(binding.root) {
    init {
        with(binding.layoutTodayFeedbackPronunciation) {
            initItemViewClickListener(
                btnTalkFeedback,
                groupTodayFeedbackPronunciation
            )
        }
    }

    override fun onBind(
        feedbackData: TodayFeedback.Pronunciation,
        isLastItem: Boolean,
        isOnlyItem: Boolean
    ) {
        val context = binding.root.context

        with(binding) {
            layoutTodayFeedbackPronunciation.btnTalkFeedback.visibility = View.VISIBLE
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationWord.text =  feedbackData.word
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationWordPronun.text =
                context.getString(
                    R.string.tv_today_saved_pronunciation,
                    feedbackData.pronunciation
                )
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationTranslation.text = feedbackData.translation
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationSentence.text = feedbackData.sentence
            layoutTodayFeedbackPronunciation.tvTodayFeedbackPronunciationAccuracy.text =
                context.getString(
                    R.string.tv_today_saved_pronunciation_accuracy,
                    feedbackData.accuracy
                )

            handleVisibility(
                isOnlyItem,
                isLastItem,
                viewTodayFeedbackCircle,
                dividerTodayFeedbackPronunciation
            )
        }
    }
}