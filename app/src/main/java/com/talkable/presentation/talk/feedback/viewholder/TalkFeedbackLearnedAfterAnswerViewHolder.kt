package com.talkable.presentation.talk.feedback.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackLearnedAfterSentenceBinding
import com.talkable.presentation.FeedbackTextColor
import com.talkable.presentation.talk.feedback.model.Learned

class TalkFeedbackLearnedAfterAnswerViewHolder(
    private val binding: ItemTalkFeedbackLearnedAfterSentenceBinding,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Learned.AfterAnswer) = with(binding) {
        model = data
        executePendingBindings()
        setAfterAnswerTextColor(data.afterFullAnswer, data.afterAnswerParts)
    }

    private fun setAfterAnswerTextColor(fullText: String, partsText: List<String>) {
        val spannableString =
            FeedbackTextColor(context).setAfterAnswerTextColor(fullText, partsText)

        binding.tvTalkFeedbackLearnedAfterSentence.text = spannableString
    }
}