package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackTopBinding
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

class TalkFeedbackTopViewHolder(private val binding: ItemTalkFeedbackTopBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: TalkFeedbackModel) = with(binding) {
        model = data
        executePendingBindings()
    }
}

