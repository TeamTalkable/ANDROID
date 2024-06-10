package com.talkable.presentation.talk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemTalkAiBinding
import com.talkable.databinding.ItemTalkUserBinding

class TalkAdapter : ListAdapter<TalkData, RecyclerView.ViewHolder>(TalkAdapterDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_AI -> ItemTalkAiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { AiTalkViewHolder(it) }

            VIEW_TYPE_USER -> ItemTalkUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { UserTalkViewHolder(it) }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is AiTalkViewHolder -> holder.onBind(message)
            is UserTalkViewHolder -> holder.onBind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val talkData = getItem(position)
        return if (talkData.type == "ai") {
            VIEW_TYPE_AI
        } else {
            VIEW_TYPE_USER
        }
    }

    companion object {
        const val VIEW_TYPE_AI = 0
        const val VIEW_TYPE_USER = 1

        private val TalkAdapterDiffCallback = ItemDiffCallback<TalkData>(
            onItemsTheSame = { old, new -> old.type == new.type },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}