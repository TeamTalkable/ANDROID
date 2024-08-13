package com.talkable.presentation.mypage.saved

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedSyntaxAdapter :
    ListAdapter<SavedWord, SavedSyntaxViewHolder>(SavedSyntaxDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSyntaxViewHolder {
        return SavedSyntaxViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedSyntaxViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class SavedSyntaxDiffCallback : DiffUtil.ItemCallback<SavedWord>() {
    override fun areItemsTheSame(oldItem: SavedWord, newItem: SavedWord): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: SavedWord, newItem: SavedWord): Boolean {
        return oldItem == newItem
    }
}