package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackLearnedGrammarBinding
import com.talkable.presentation.talk.feedback.model.Learned

class TalkFeedbackLearnedGrammarViewHolder(private val binding: ItemTalkFeedbackLearnedGrammarBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Learned.Grammar) = with(binding) {
        model = data
        executePendingBindings()
    }
}