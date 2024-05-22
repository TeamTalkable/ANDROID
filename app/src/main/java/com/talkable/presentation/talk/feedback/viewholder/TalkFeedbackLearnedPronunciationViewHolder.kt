package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackLearnedPronunciationBinding
import com.talkable.presentation.talk.feedback.model.Learned

class TalkFeedbackLearnedPronunciationViewHolder(private val binding: ItemTalkFeedbackLearnedPronunciationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Learned.Pronunciation) = with(binding) {
        model = data
        executePendingBindings()
    }
}