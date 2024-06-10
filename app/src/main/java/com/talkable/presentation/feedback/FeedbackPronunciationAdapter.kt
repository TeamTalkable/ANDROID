package com.talkable.presentation.feedback

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemPronunciationWordBinding
import com.talkable.presentation.feedback.viewholder.PronunciationViewHolder
import com.talkable.presentation.talk.feedback.model.Learned

class FeedbackPronunciationAdapter(context: Context, private val onClickWordItem: (Int) -> Unit) :
    ListAdapter<Learned.Pronunciation, PronunciationViewHolder>(
        pronunciationWordDiffCallback
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PronunciationViewHolder {
        val binding =
            ItemPronunciationWordBinding.inflate(inflater, parent, false)
        return PronunciationViewHolder(binding, onClickWordItem)
    }

    override fun onBindViewHolder(holder: PronunciationViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    companion object {
        private val pronunciationWordDiffCallback =
            ItemDiffCallback<Learned.Pronunciation>(onItemsTheSame = { old, new -> old.englishWord == new.englishWord },
                onContentsTheSame = { old, new -> old == new })
    }
}

