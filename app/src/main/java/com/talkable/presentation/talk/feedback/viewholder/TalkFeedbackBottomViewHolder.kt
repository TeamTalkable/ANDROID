package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackBottomBinding
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

class TalkFeedbackBottomViewHolder(private val binding: ItemTalkFeedbackBottomBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: TalkFeedbackModel) = with(binding) {
        model = data
        executePendingBindings()
    }
}