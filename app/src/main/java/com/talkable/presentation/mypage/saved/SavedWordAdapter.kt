package com.talkable.presentation.mypage.saved

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedWordAdapter : ListAdapter<SavedWord, SavedWordViewHolder>(SavedWordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedWordViewHolder {
        return SavedWordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedWordViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class SavedWordDiffCallback : DiffUtil.ItemCallback<SavedWord>() {
    override fun areItemsTheSame(oldItem: SavedWord, newItem: SavedWord): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: SavedWord, newItem: SavedWord): Boolean {
        return oldItem == newItem
    }
}