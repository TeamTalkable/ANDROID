package com.talkable.presentation.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemPronunciationWordBinding
import com.talkable.presentation.talk.feedback.model.Learned

class PronunciationWordViewHolder(
    private val binding: ItemPronunciationWordBinding,
    private val onClickWordItem: (Learned.Pronunciation, Boolean, Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: Learned.Pronunciation, isSelected: Boolean) = with(binding) {
        model = data
        root.isSelected = isSelected
        executePendingBindings()
        root.setOnClickListener {
            onClickWordItem(data, root.isSelected, absoluteAdapterPosition)
            root.isSelected = !root.isSelected
        }
    }
}