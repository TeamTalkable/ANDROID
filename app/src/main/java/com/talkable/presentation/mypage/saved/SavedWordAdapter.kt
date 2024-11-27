package com.talkable.presentation.mypage.saved

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.talkable.presentation.review.model.Saved

class SavedWordAdapter : ListAdapter<Saved.Word, SavedWordViewHolder>(SavedWordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedWordViewHolder {
        return SavedWordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedWordViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class SavedWordDiffCallback : DiffUtil.ItemCallback<Saved.Word>() {
    override fun areItemsTheSame(oldItem: Saved.Word, newItem: Saved.Word): Boolean {
        return oldItem.wordEnglish == newItem.wordEnglish
    }

    override fun areContentsTheSame(oldItem: Saved.Word, newItem: Saved.Word): Boolean {
        return oldItem == newItem
    }
}