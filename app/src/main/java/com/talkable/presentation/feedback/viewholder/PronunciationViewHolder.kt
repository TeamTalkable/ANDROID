package com.talkable.presentation.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemPronunciationWordBinding
import com.talkable.presentation.talk.feedback.model.Learned

class PronunciationViewHolder(private val binding: ItemPronunciationWordBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Learned.Pronunciation) = with(binding) {
        model = data
        executePendingBindings()
    }
}