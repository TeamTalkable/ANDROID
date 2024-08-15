package com.talkable.presentation.feedback.today

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.talkable.presentation.feedback.today.model.TodaySaved

class TodaySavedAdapter : ListAdapter<TodaySaved, TodaySavedViewHolder>(SavedWordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodaySavedViewHolder {
        return TodaySavedViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodaySavedViewHolder, position: Int) {
        val isLastItem = position == itemCount - 1
        holder.onBind(getItem(position), isLastItem)
    }
}

class SavedWordDiffCallback : DiffUtil.ItemCallback<TodaySaved>() {
    override fun areItemsTheSame(oldItem: TodaySaved, newItem: TodaySaved): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: TodaySaved, newItem: TodaySaved): Boolean {
        return oldItem == newItem
    }
}