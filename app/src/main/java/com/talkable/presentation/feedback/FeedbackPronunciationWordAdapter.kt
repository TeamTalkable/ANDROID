package com.talkable.presentation.feedback

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemPronunciationWordBinding
import com.talkable.presentation.feedback.viewholder.PronunciationWordViewHolder
import com.talkable.presentation.talk.feedback.model.Learned

class FeedbackPronunciationWordAdapter(
    context: Context,
    private val onClickWordItem: (Learned.Pronunciation, Boolean) -> Unit
) :
    ListAdapter<Learned.Pronunciation, PronunciationWordViewHolder>(
        pronunciationWordDiffCallback
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var selectedItemPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PronunciationWordViewHolder {
        val binding =
            ItemPronunciationWordBinding.inflate(inflater, parent, false)
        return PronunciationWordViewHolder(binding, onClickWordItem) {
            selectItem(it)
        }
    }

    override fun onBindViewHolder(holder: PronunciationWordViewHolder, position: Int) {
        val isSelected = position == selectedItemPosition
        holder.onBind(currentList[position], isSelected)
    }

    fun selectItem(position: Int) {
        if (position != selectedItemPosition) {
            val oldPosition = selectedItemPosition
            selectedItemPosition = position
            notifyItemChanged(oldPosition)
            notifyItemChanged(position)
        }
    }

    companion object {
        private val pronunciationWordDiffCallback =
            ItemDiffCallback<Learned.Pronunciation>(onItemsTheSame = { old, new -> old.englishWord == new.englishWord },
                onContentsTheSame = { old, new -> old.isSelected == new.isSelected })
    }
}

