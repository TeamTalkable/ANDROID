package com.talkable.presentation.talk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.talkable.core.view.ItemDiffCallback
import com.talkable.data.dto.request.Message
import com.talkable.databinding.ItemTalkAiBinding
import com.talkable.databinding.ItemTalkUserBinding

class TalkAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(TalkAdapterDiffCallback) {
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
        return if (talkData.role == "ai") {
            VIEW_TYPE_AI
        } else {
            VIEW_TYPE_USER
        }
    }

    companion object {
        const val VIEW_TYPE_AI = 0
        const val VIEW_TYPE_USER = 1

        private val TalkAdapterDiffCallback = ItemDiffCallback<Message>(
            onItemsTheSame = { old, new -> old.role == new.role },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}