package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackLearnedLabelBinding
import com.talkable.presentation.talk.feedback.model.Learned

class TalkFeedbackLearnedLabelViewHolder(private val binding: ItemTalkFeedbackLearnedLabelBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Learned.Label) = with(binding) {
        model = data
        executePendingBindings()
    }
}