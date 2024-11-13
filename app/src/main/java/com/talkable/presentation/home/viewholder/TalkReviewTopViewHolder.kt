package com.talkable.presentation.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkReviewBinding
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

class TalkReviewTopViewHolder(private val binding: ItemTalkReviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: TalkFeedbackModel) = with(binding) {
        model = data
        executePendingBindings()
    }
}