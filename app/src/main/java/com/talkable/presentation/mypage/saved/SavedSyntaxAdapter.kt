package com.talkable.presentation.mypage.saved

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.talkable.presentation.home.model.Saved

class SavedSyntaxAdapter :
    ListAdapter<Saved.Sentence, SavedSyntaxViewHolder>(SavedSyntaxDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSyntaxViewHolder {
        return SavedSyntaxViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedSyntaxViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class SavedSyntaxDiffCallback : DiffUtil.ItemCallback<Saved.Sentence>() {
    override fun areItemsTheSame(oldItem: Saved.Sentence, newItem: Saved.Sentence): Boolean {
        return oldItem.sentenceEnglish == newItem.sentenceEnglish
    }

    override fun areContentsTheSame(oldItem: Saved.Sentence, newItem: Saved.Sentence): Boolean {
        return oldItem == newItem
    }
}