package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackLearnedExpressionBinding
import com.talkable.presentation.talk.feedback.model.Learned

class TalkFeedbackLearnedExpressionViewHolder(private val binding: ItemTalkFeedbackLearnedExpressionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Learned.Expression) = with(binding) {
        model = data
        executePendingBindings()
    }
}